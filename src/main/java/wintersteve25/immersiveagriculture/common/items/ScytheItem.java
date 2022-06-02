package wintersteve25.immersiveagriculture.common.items;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.api.events.IAHooks;
import wintersteve25.immersiveagriculture.api.events.ScytheHarvestEvent;
import wintersteve25.immersiveagriculture.common.init.IAEnchantments;
import wintersteve25.immersiveagriculture.common.utils.Utils;

import java.util.Set;

public class ScytheItem extends ToolItem implements IFCItem {

    private static final Set<Block> EFFECTIVE_BLOCKS = ImmutableSet.of();

    public ScytheItem(IItemTier tier, float attackSpeed, float baseAttackDamage) {
        super(baseAttackDamage, attackSpeed, tier, EFFECTIVE_BLOCKS, new Properties().group(ImmersiveAgriculture.creativeTab));
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return true;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos pos = context.getPos();
        World world = context.getWorld();

        if (!world.isRemote()) {
            BlockState state = world.getBlockState(pos);
            if (!state.isAir(world, pos)) {
                if (context.getPlayer() != null) {
                        if (state.getBlock() instanceof CropsBlock) {
                            CropsBlock crop = (CropsBlock) state.getBlock();
                            if (crop.isMaxAge(world.getBlockState(pos))) {
                                ScytheHarvestEvent event = IAHooks.onScytheHarvestCancel(context);
                                if (!event.isCanceled()) {
                                    int bonus = EnchantmentHelper.getEnchantmentLevel(IAEnchantments.FARMER_LUCK, context.getItem());
                                    Utils.harvest(world, state, pos, context, bonus);
                                    context.getPlayer().addExhaustion(0.4F);
                                    world.playSound(null, context.getPlayer().getPosX(), context.getPlayer().getPosY(), context.getPlayer().getPosZ(), SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                                    context.getItem().damageItem(1, context.getPlayer(), (item) -> item.sendBreakAnimation(context.getHand()));

                                    switch ((ItemTier) getTier()) {
                                        case WOOD:
                                            context.getPlayer().getCooldownTracker().setCooldown(getItem(), 40);
                                            break;
                                        case STONE:
                                            context.getPlayer().getCooldownTracker().setCooldown(getItem(), 35);
                                            break;
                                        case IRON:
                                            context.getPlayer().getCooldownTracker().setCooldown(getItem(), 30);
                                            break;
                                        case GOLD:
                                            context.getPlayer().getCooldownTracker().setCooldown(getItem(), 10);
                                            break;
                                        case DIAMOND:
                                            context.getPlayer().getCooldownTracker().setCooldown(getItem(), 15);
                                            break;
                                        case NETHERITE:
                                            context.getPlayer().getCooldownTracker().setCooldown(getItem(), 12);
                                            break;
                                    }
                                }
                                return event.getActionResult();
                            }
                        }
                    } else {
                        context.getPlayer().setActiveHand(context.getHand());
                    }
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public String regName() {
        switch ((ItemTier) getTier()) {
            case WOOD:
                return "Wooden Scythe";
            case STONE:
                return "Stone Scythe";
            case GOLD:
                return "Golden Scythe";
            case DIAMOND:
                return "Diamond Scythe";
            case IRON:
                return "Iron Scythe";
            case NETHERITE:
                return "Netherite Scythe";
            default:
                return "Unknown Scythe";
        }
    }

    @Override
    public Item ogItem() {
        return this;
    }
}
