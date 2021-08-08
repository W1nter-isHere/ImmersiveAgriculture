package wintersteve25.immersiveagriculture.common.utils;

import fictioncraft.wintersteve25.fclib.common.helper.MiscHelper;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.items.ScytheItem;

import java.util.Random;

public class Utils {
    public static final Item.Properties BaseItemProperties = new Item.Properties().group(ImmersiveAgriculture.creativeTab);
    public static final BooleanProperty CAN_GROW = BooleanProperty.create("can_grow");
    public static final IntegerProperty FERTILIZER_LEVEL = IntegerProperty.create("fertilizer_level", 0, 4);

    public static final EnchantmentType SCYTHE = EnchantmentType.create("SCYTHE", h -> h instanceof ScytheItem);

    public static void harvest(World world, BlockState state, BlockPos pos, ItemUseContext context, int bonus) {
        LootContext.Builder lootContext = new LootContext.Builder((ServerWorld) world).withParameter(LootParameters.field_237457_g_, new Vector3d(pos.getX(), pos.getY(), pos.getZ())).withParameter(LootParameters.BLOCK_STATE, state).withParameter(LootParameters.THIS_ENTITY, context.getPlayer()).withParameter(LootParameters.TOOL, context.getItem());
        for (ItemStack stacks : state.getDrops(lootContext)) {
            InventoryHelper.spawnItemStack(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), stacks);
            stacks.setCount(MiscHelper.randomInRange(1, 8));
            if (bonus > 0) {
                InventoryHelper.spawnItemStack(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), stacks);
            }
        }
        BlockState newState = state.with(CropsBlock.AGE, 0);
        world.setBlockState(pos, newState);
    }

    /**
     * Method modified from https://github.com/codetaylor/watercan-1.16/blob/master/src/main/java/com/codetaylor/mc/watercan/common/item/WatercanBaseItem.java
     * Under Apache 2.0 license
     */
    public static void water(World world, double posX, double posY, double posZ, int range) {

        BlockPos pos;

        int blockX = (int) Math.floor(posX);
        int blockY = (int) Math.floor(posY);
        int blockZ = (int) Math.floor(posZ);

        for (int x = blockX - range; x <= blockX + range; x++) {
            for (int y = blockY - range; y <= blockY + range; y++) {
                for (int z = blockZ - range; z <= blockZ + range; z++) {
                    pos = new BlockPos(x, y, z);
                    BlockState blockState = world.getBlockState(pos);
                    Block block = blockState.getBlock();

                    // skip air blocks
                    if (!world.isAirBlock(pos)) {
                        Utils.waterBlock(world, pos, blockState, block);
                        spawnParticles(world, pos, 8);
                    }
                }
            }
        }
    }

    public static void spawnParticles(World worldIn, BlockPos pos, int amount) {
        if (worldIn.isRemote()) {
            double xpos = pos.getX() + 0.5;
            double ypos = pos.getY() + 1.0;
            double zpos = pos.getZ() + 0.5;

            for (int i = 0; i < amount; i++) {
                worldIn.addParticle(ParticleTypes.DRIPPING_WATER, xpos, ypos, zpos, 0.6, 0.3, 0.6);
            }
        }
    }

    /**
     * Method modified from https://github.com/codetaylor/watercan-1.16/blob/master/src/main/java/com/codetaylor/mc/watercan/common/item/WatercanBaseItem.java
     * Under Apache 2.0 license
     */
    public static void waterBlock(World world, BlockPos pos, BlockState blockState, Block block) {
        // moisturize farmland
        if (!world.isRemote()) {
            if (block instanceof FarmlandBlock && blockState.get(FarmlandBlock.MOISTURE) < 7) {
                BlockState newBlockState = block.getDefaultState().with(FarmlandBlock.MOISTURE, blockState.get(FarmlandBlock.MOISTURE)+1);
                world.setBlockState(pos, newBlockState, 3);
            }

            randomTick(blockState, block, world, pos);
        }
    }

    public static void randomTick(BlockState blockState, Block block, World world, BlockPos pos) {
        int blockUpdateDelay = Utils.getBlockUpdateDelay(blockState, block);

        if (blockUpdateDelay > 0 && block.ticksRandomly(blockState) && new Random().nextInt(40) > blockUpdateDelay) {
            blockState.randomTick((ServerWorld) world, pos, new Random());
        }
    }

    /**
     * Method modified from https://github.com/codetaylor/watercan-1.16/blob/master/src/main/java/com/codetaylor/mc/watercan/common/item/WatercanBaseItem.java
     * Under Apache 2.0 license
     */
    public static int getBlockUpdateDelay(BlockState blockState, Block block) {

        int delay = -1;
        int delayModifier = 18;

        if (block == Blocks.GRASS_BLOCK) {
            delay = delayModifier;
        } else if (block == Blocks.MYCELIUM) {
            delay = delayModifier;
        } else if (block == Blocks.WHEAT) {
            delay = (int) (2.0f * delayModifier);
        } else if (block instanceof SaplingBlock) {
            delay = (int) (2.5f + delayModifier);

        } else if (block instanceof IPlantable || block instanceof IGrowable) {
            delay = (int) (2.0f + delayModifier);

        } else if (blockState.getMaterial() == Material.ORGANIC) {
            delay = delayModifier;
        }
        return delay;
    }
}
