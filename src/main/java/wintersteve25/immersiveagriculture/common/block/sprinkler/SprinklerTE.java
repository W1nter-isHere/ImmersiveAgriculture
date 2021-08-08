package wintersteve25.immersiveagriculture.common.block.sprinkler;

import fictioncraft.wintersteve25.fclib.common.base.FCLibTE;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.immersiveagriculture.common.block.FCEnergyStorage;
import wintersteve25.immersiveagriculture.common.config.IAConfigs;
import wintersteve25.immersiveagriculture.common.init.IABlocks;
import wintersteve25.immersiveagriculture.common.utils.Utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SprinklerTE extends FCLibTE implements ITickableTileEntity, IAnimatable {

    private final FluidTank fluidTank = new FluidTank(16000) {
        @Override
        protected void onContentsChanged() {
            SprinklerTE.super.updateBlock();
        }
    };
    private final LazyOptional<IFluidHandler> fluidOptional = LazyOptional.of(() -> fluidTank);
    private final FCEnergyStorage energyStorage = new FCEnergyStorage(8092);
    private final LazyOptional<IEnergyStorage> energyOptional = LazyOptional.of(() -> energyStorage);
    private final AnimationFactory manager = new AnimationFactory(this);

    private int operationTicks = IAConfigs.SPRINKLER_OPERATION_LENGTH.get();

    public SprinklerTE() {
        super(IABlocks.SPRINKLER_TE.get());
        fluidTank.setValidator((fluid) -> fluid.getFluid() == Fluids.WATER);
    }

    @Override
    public void tick() {
        if (!world.isRemote()) {
            if (fluidTank.getFluid().isFluidEqual(new FluidStack(Fluids.WATER, 1000))) {
                if (fluidTank.getFluidAmount() > IAConfigs.SPRINKLER_CONSUME_WATER_AMOUNT.get()) {
                    if (energyStorage.getEnergyStored() > IAConfigs.SPRINKLER_OPERATION_POWER.get()) {
                        setWorking(false);
                        progress--;
                        if (progress <= 0) {
                            setWorking(true);
                            operationTicks--;
                            energyStorage.extractEnergy(IAConfigs.SPRINKLER_OPERATION_POWER.get(), false);
                            fluidTank.drain(IAConfigs.SPRINKLER_CONSUME_WATER_AMOUNT.get(), IFluidHandler.FluidAction.EXECUTE);
                            Utils.water(world, pos.getX(), pos.getY() + 0.5, pos.getZ() + 0.5, IAConfigs.SPRINKLER_RANGE.get());
                            if (operationTicks <= 0) {
                                setWorking(false);
                                setProgress(this.progress());
                                operationTicks = IAConfigs.SPRINKLER_OPERATION_LENGTH.get();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        fluidTank.readFromNBT(tag.getCompound("tank"));
        energyStorage.read(tag.getCompound("energy"));
        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        CompoundNBT nbt = new CompoundNBT();
        fluidTank.writeToNBT(nbt);
        tag.put("tank", nbt);
        tag.put("energy", energyStorage.write());
        return super.write(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidOptional.cast();
        } else if (cap == CapabilityEnergy.ENERGY) {
            return energyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected int progress() {
        return IAConfigs.SPRINKLER_OPERATION_TIME.get();
    }

    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 80;
        if (super.getWorking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("sprinkler.work", true));
            return PlayState.CONTINUE;
        } else {
            event.getController().setAnimation(new AnimationBuilder());
            return PlayState.STOP;
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return manager;
    }
}
