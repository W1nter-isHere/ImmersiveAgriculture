package wintersteve25.immersiveagriculture.common.mixin;

import fictioncraft.wintersteve25.fclib.common.helper.MiscHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import wintersteve25.immersiveagriculture.common.config.IAConfigs;

import javax.annotation.Nullable;

@Mixin(Block.class)
public class BlockMixin {

    /**
     * @author og by mojang mixin by wintersteve25
     */
    @Overwrite
    public static void spawnDrops(BlockState state, World worldIn, BlockPos pos, @Nullable TileEntity tileEntityIn, Entity entityIn, ItemStack stack) {
        if (worldIn instanceof ServerWorld) {
            if (state.getBlock() instanceof CropsBlock) {
                CropsBlock growable = (CropsBlock) state.getBlock();
                if (growable.isMaxAge(state)) {
                    Block.getDrops(state, (ServerWorld)worldIn, pos, tileEntityIn, entityIn, stack).forEach((stackToSpawn) -> {
                        if (MiscHelper.chanceHandling(IAConfigs.CHANCE_WITHOUT_SCYTHE.get())) {
                            Block.spawnAsEntity(worldIn, pos, stackToSpawn);
                        }
                    });
                    state.spawnAdditionalDrops((ServerWorld)worldIn, pos, stack);
                    return;
                }
            }

            Block.getDrops(state, (ServerWorld)worldIn, pos, tileEntityIn, entityIn, stack).forEach((stackToSpawn) -> {
                Block.spawnAsEntity(worldIn, pos, stackToSpawn);
            });
            state.spawnAdditionalDrops((ServerWorld)worldIn, pos, stack);
        }
    }
}
