package wintersteve25.immersiveagriculture.common.block.sprinkler;

import fictioncraft.wintersteve25.fclib.common.helper.VoxelShapeHelper;
import fictioncraft.wintersteve25.fclib.common.interfaces.IFCDataGenObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
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
import java.util.stream.Stream;

public class SprinklerBlock extends HorizontalFaceBlock implements IFCDataGenObject<Block> {
    private static final VoxelShape NORTH = Stream.of(
            Block.makeCuboidShape(3, 0, 7, 13, 2, 9),
            Block.makeCuboidShape(3, 1, 9, 13, 1, 10),
            Block.makeCuboidShape(0, 0, 11, 16, 1, 12),
            Block.makeCuboidShape(0, 0, 4, 16, 1, 5),
            Block.makeCuboidShape(13, 0, 5, 16, 1, 11),
            Block.makeCuboidShape(0, 0, 5, 3, 1, 11)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    private static final VoxelShape SOUTH = VoxelShapeHelper.rotate(NORTH, Rotation.CLOCKWISE_180);
    private static final VoxelShape EAST = VoxelShapeHelper.rotate(NORTH, Rotation.CLOCKWISE_90);
    private static final VoxelShape WEST = VoxelShapeHelper.rotate(NORTH, Rotation.COUNTERCLOCKWISE_90);

    private static final VoxelShape NORTH_WALL = Stream.of(
            Block.makeCuboidShape(3, 9, 15, 13, 10, 15),
            Block.makeCuboidShape(3, 7, 14, 13, 9, 16),
            Block.makeCuboidShape(0, 11, 15, 16, 12, 16),
            Block.makeCuboidShape(0, 4, 15, 16, 5, 16),
            Block.makeCuboidShape(0, 5, 15, 3, 11, 16),
            Block.makeCuboidShape(13, 5, 15, 16, 11, 16)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    private static final VoxelShape SOUTH_WALL = VoxelShapeHelper.rotate(NORTH_WALL, Rotation.CLOCKWISE_180);
    private static final VoxelShape EAST_WALL = VoxelShapeHelper.rotate(NORTH_WALL, Rotation.CLOCKWISE_90);
    private static final VoxelShape WEST_WALL = VoxelShapeHelper.rotate(NORTH_WALL, Rotation.COUNTERCLOCKWISE_90);

    private static final VoxelShape UP = VoxelShapeHelper.rotate(NORTH, Direction.UP);
    private static final VoxelShape SOUTH_UP = VoxelShapeHelper.rotate(UP, Rotation.CLOCKWISE_180);
    private static final VoxelShape EAST_UP = VoxelShapeHelper.rotate(UP, Rotation.CLOCKWISE_90);
    private static final VoxelShape WEST_UP = VoxelShapeHelper.rotate(UP, Rotation.COUNTERCLOCKWISE_90);

    public SprinklerBlock(Properties properties) {
        super(properties);
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
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(HORIZONTAL_FACING);
        switch(state.get(FACE)) {
            case FLOOR:
                switch (direction) {
                    case EAST:
                        return EAST;
                    case WEST:
                        return WEST;
                    case SOUTH:
                        return SOUTH;
                    case UP:
                        return UP;
                    default:
                        return NORTH;
                }
            case WALL:
                switch(direction) {
                    case EAST:
                        return EAST_WALL;
                    case WEST:
                        return WEST_WALL;
                    case SOUTH:
                        return SOUTH_WALL;
                    default:
                        return NORTH_WALL;
                }
            default:
                switch (direction) {
                    case SOUTH:
                        return SOUTH_UP;
                    case WEST:
                        return WEST_UP;
                    case EAST:
                        return EAST_UP;
                    default:
                        return UP;
                }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, FACE);
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

    @Override
    public String regName() {
        return "Sprinkler";
    }

    @Override
    public Block getOg() {
        return this;
    }
}