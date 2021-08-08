package wintersteve25.immersiveagriculture.common.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CropsBlock.class)
public abstract class CropBlockMixin {
    /**
     * @author original by mojang mixin by wintersteve25
     */
    @Overwrite
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.getBlock() instanceof FarmlandBlock;
    }
}
