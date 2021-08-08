package wintersteve25.immersiveagriculture.common.mixin;

import fictioncraft.wintersteve25.fclib.common.helper.MiscHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import wintersteve25.immersiveagriculture.common.config.IAConfigs;

import java.util.Random;

import static net.minecraft.block.FarmlandBlock.MOISTURE;
import static net.minecraft.block.FarmlandBlock.turnToDirt;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin {

    @Shadow
    private static boolean hasWater(IWorldReader worldIn, BlockPos pos) {
        return false;
    }

    @Shadow
    protected abstract boolean hasCrops(IBlockReader worldIn, BlockPos pos);

    /**
     * @author og by mojang mixin by wintersteve25
     */
    @Overwrite
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isRemote()) {
            int i = state.get(MOISTURE);
            if (!hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up())) {
                if (i > 0) {
                    worldIn.setBlockState(pos, state.with(MOISTURE, Integer.valueOf(i - 1)), 2);
                } else if (!hasCrops(worldIn, pos)) {
                    turnToDirt(state, worldIn, pos);
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
        }
    }
}
