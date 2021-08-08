package wintersteve25.immersiveagriculture.common.block.sprinkler;

import fictioncraft.wintersteve25.fclib.common.base.FCLibDirectionalBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
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
import wintersteve25.immersiveagriculture.common.init.IABlocks;
import wintersteve25.immersiveagriculture.common.items.WateringCanItem;

import javax.annotation.Nullable;

public class SprinklerBlock extends FCLibDirectionalBlock {
    public SprinklerBlock(Properties properties) {
        super(properties, "Sprinkler");
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote()) {
            ItemStack heldItem = player.getHeldItem(handIn);

            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof SprinklerTE) {
                SprinklerTE te = (SprinklerTE) tile;
                IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, hit.getFace()).orElse(null);

                if (heldItem.isEmpty()) {
                    if (player.isSneaking()) {
                        FluidStack stack = handler.getFluidInTank(1);
                        player.sendStatusMessage(new TranslationTextComponent("immersiveagriculture.sprinkler", stack.getAmount(), stack.getFluid().getAttributes().getDisplayName(stack)), true);
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
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return IABlocks.SPRINKLER_TE.get().create();
    }
}