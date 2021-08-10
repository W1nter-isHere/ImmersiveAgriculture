package wintersteve25.immersiveagriculture.common.block.hydroponic_tile;

import fictioncraft.wintersteve25.fclib.common.base.FCLibTE;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import wintersteve25.immersiveagriculture.common.init.IABlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HydroponicTileTE extends FCLibTE {

    private final FluidTank fluidTank = new FluidTank(4000) {
        @Override
        protected void onContentsChanged() {
            HydroponicTileTE.super.updateBlock();
        }
    };
    private final LazyOptional<IFluidHandler> lazyOptional = LazyOptional.of(() -> fluidTank);

    public HydroponicTileTE() {
        super(IABlocks.HYDROPONIC_TILE_TE.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        fluidTank.readFromNBT(tag.getCompound("tank"));
        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        CompoundNBT nbt = new CompoundNBT();
        fluidTank.writeToNBT(nbt);
        tag.put("tank", nbt);
        return super.write(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return lazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected int progress() {
        return 0;
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }
}
