package wintersteve25.immersiveagriculture.common.block.farm_tile;

import fictioncraft.wintersteve25.fclib.common.helper.MiscHelper;
import fictioncraft.wintersteve25.fclib.common.interfaces.IFCDataGenObject;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import wintersteve25.immersiveagriculture.common.config.IAConfigs;
import wintersteve25.immersiveagriculture.common.init.IAItems;
import wintersteve25.immersiveagriculture.common.utils.Utils;

import java.util.Random;

public class FarmTileBlock extends FarmlandBlock implements IFCDataGenObject<Block> {
    public static final IntegerProperty FERTILIZATION = Utils.FERTILIZER_LEVEL;

    private final boolean advanced;

    public FarmTileBlock(Properties properties, boolean advanced) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(MOISTURE, Integer.valueOf(0)).with(FERTILIZATION, Integer.valueOf(0)));

        this.advanced = advanced;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
        builder.add(FERTILIZATION);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        int i = state.get(MOISTURE);
        if (!hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up())) {
            if (i > 0) {
                worldIn.setBlockState(pos, state.with(MOISTURE, Integer.valueOf(i - 1)), 2);
            }
        } else if (i < 7) {
            worldIn.setBlockState(pos, state.with(MOISTURE, Integer.valueOf(i++)), 2);
            if (MiscHelper.chanceHandling(IAConfigs.CHANCE_CONSUME_WATER.get())) {
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
                    if (worldIn.getFluidState(blockpos).isTagged(FluidTags.WATER)) {
                        worldIn.setBlockState(pos, state.with(MOISTURE, Integer.valueOf(i + 1)), 2);
                        worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        int j = state.get(getFertilization());
        if (j > 0 && i > 0) {
            if (hasCrops(worldIn, pos)) {
                BlockState plant = worldIn.getBlockState(pos.up());
                if (plant.getBlock() instanceof CropsBlock) {
                    CropsBlock growable = (CropsBlock) plant.getBlock();
                    if (growable.canGrow(worldIn, pos.up(), plant, worldIn.isRemote())) {
                        growable.grow(worldIn, pos.up(), plant);
                        worldIn.setBlockState(pos, state.with(getFertilization(), Integer.valueOf(j - 1)), 2);
                        spawnParticles(worldIn, pos.up(), 5);
                    }
                }
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
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
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    public void spawnParticles(World worldIn, BlockPos pos, int amount) {
        if (worldIn.isRemote()) {
            double xpos = pos.getX() + 0.5;
            double ypos = pos.getY() + 1.0;
            double zpos = pos.getZ() + 0.5;

            for (int i = 0; i < amount; i++) {
                worldIn.addParticle(ParticleTypes.HAPPY_VILLAGER, xpos, ypos, zpos, 0.6, 0.3, 0.6);
            }
        }
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.onLivingFall(fallDistance, 1.0F);
    }

    public IntegerProperty getFertilization() {
        return FERTILIZATION;
    }

    public boolean isAdvanced() {
        return advanced;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.create(0, 0, 0, 1, 1, 1);
    }

    protected static boolean hasWater(IWorldReader worldIn, BlockPos pos) {
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
            if (worldIn.getFluidState(blockpos).isTagged(FluidTags.WATER)) {
                return true;
            }
        }

        return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(worldIn, pos);
    }

    protected boolean hasCrops(IBlockReader worldIn, BlockPos pos) {
        BlockState plant = worldIn.getBlockState(pos.up());
        BlockState state = worldIn.getBlockState(pos);
        return plant.getBlock() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(worldIn, pos, Direction.UP, (net.minecraftforge.common.IPlantable) plant.getBlock());
    }

    @Override
    public String regName() {
        return advanced ? "Hydroponic Tile" : "Farm Tile";
    }

    @Override
    public Block getOg() {
        return this;
    }
}
