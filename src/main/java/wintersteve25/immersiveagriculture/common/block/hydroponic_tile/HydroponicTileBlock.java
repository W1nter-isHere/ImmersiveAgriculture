package wintersteve25.immersiveagriculture.common.block.hydroponic_tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import wintersteve25.immersiveagriculture.common.block.farm_tile.FarmTileBlock;
import wintersteve25.immersiveagriculture.common.init.IABlocks;
import wintersteve25.immersiveagriculture.common.init.IAItems;
import wintersteve25.immersiveagriculture.common.items.WateringCanItem;

import javax.annotation.Nullable;

public class HydroponicTileBlock extends FarmTileBlock {
    public HydroponicTileBlock(Properties properties) {
        super(properties, true);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
        if (!worldIn.isRemote()) {
            ItemStack heldItem = player.getHeldItem(handIn);
            if (heldItem.getItem() == IAItems.FERTILIZER) {
                int i = state.get(getFertilization());
                if (i == 4) {
                    player.sendStatusMessage(new TranslationTextComponent("immersiveagriculture.farmtile.reachlimit"), true);
                    return ActionResultType.FAIL;
                } else if (i < 4) {
                    worldIn.setBlockState(pos, state.with(getFertilization(), Integer.valueOf(i + 1)), 2);
                    spawnParticles(worldIn, pos, 3);
                    if (!player.isCreative()) {
                        heldItem.shrink(1);
                    }
                    return ActionResultType.SUCCESS;
                }
            }

            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof HydroponicTileTE) {
                HydroponicTileTE te = (HydroponicTileTE) tile;
                IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, hit.getFace()).orElse(null);

                if (heldItem.isEmpty()) {
                    if (player.isSneaking()) {
                        FluidStack stack = handler.getFluidInTank(1);
                        player.sendStatusMessage(new TranslationTextComponent("immersiveagriculture.hydroponic_tile", stack.getAmount(), stack.getFluid().getAttributes().getDisplayName(stack)), true);
                    }
                }

                if (FluidUtil.getFluidHandler(player.getHeldItem(handIn)).isPresent() && !(heldItem.getItem() instanceof WateringCanItem)) {
                    FluidUtil.interactWithFluidHandler(player, handIn, handler);
                    return ActionResultType.SUCCESS;
                }
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof HydroponicTileTE) {
            HydroponicTileTE te = (HydroponicTileTE) tile;
            if (!te.getFluidTank().getFluid().isEmpty()) {
                return te.getFluidTank().getFluid().getFluid().getAttributes().getLuminosity();
            }
        }
        return super.getLightValue(state, world, pos);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return IABlocks.HYDROPONIC_TILE_TE.get().create();
    }
}
