package wintersteve25.immersiveagriculture.common.items;

import com.google.common.collect.ImmutableSet;
import fictioncraft.wintersteve25.fclib.common.helper.MiscHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.*;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.client.renderers.ScytheRenderer;
import wintersteve25.immersiveagriculture.common.init.IAEnchantments;
import wintersteve25.immersiveagriculture.common.utils.Utils;

import java.util.Set;

public class ScytheItem extends ToolItem implements IFCItem, IAnimatable {

    private static final Set<Block> EFFECTIVE_BLOCKS = ImmutableSet.of();
    private static boolean isCharged = false;
    public AnimationFactory factory = new AnimationFactory(this);
    public String controllerName = "controller";
    private static final int ANIM_OPEN = 0;

    public ScytheItem(IItemTier tier, float attackSpeed, float baseAttackDamage, boolean registerISyncable) {
        super(baseAttackDamage, attackSpeed, tier, EFFECTIVE_BLOCKS, new Properties().group(ImmersiveAgriculture.creativeTab).setISTER(() -> ScytheRenderer::new));

//        if (registerISyncable) {
//            GeckoLibNetwork.registerSyncable(this);
//        }
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
//                    if (isCharged) {
                        if (state.getBlock() instanceof CropsBlock) {
                            CropsBlock crop = (CropsBlock) state.getBlock();
                            if (crop.isMaxAge(world.getBlockState(pos))) {
                                int bonus = EnchantmentHelper.getEnchantmentLevel(IAEnchantments.FARMER_LUCK, context.getItem());
                                Utils.harvest(world, state, pos, context, bonus);

//                                // Gets the item that the player is holding, should be a JackInTheBoxItem
//                                final ItemStack stack = context.getPlayer().getHeldItem(context.getHand());
//                                final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) world);
//                                // Tell all nearby clients to trigger this JackInTheBoxItem
//                                final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> context.getPlayer());
//                                GeckoLibNetwork.syncAnimation(target, this, id, ANIM_OPEN);

                                world.playSound((PlayerEntity)null, context.getPlayer().getPosX(), context.getPlayer().getPosY(), context.getPlayer().getPosZ(), SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
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
                                return ActionResultType.SUCCESS;
                            }
                        }
                    } else {
                        context.getPlayer().setActiveHand(context.getHand());
                    }
//                }
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

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, controllerName, 1, this::predicate));
    }

    public <P extends BlockItem & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

//    @Override
//    public void onAnimationSync(int i, int i1) {
//        if (i1 == ANIM_OPEN) {
//            // Always use GeckoLibUtil to get AnimationControllers when you don't have
//            // access to an AnimationEvent
//            final AnimationController controller = GeckoLibUtil.getControllerForID(this.factory, i, controllerName);
//
//            if (controller.getAnimationState() == AnimationState.Stopped) {
//                // If you don't do this, the popup animation will only play once because the
//                // animation will be cached.
//                controller.markNeedsReload();
//                // Set the animation to open the JackInTheBoxItem which will start playing music
//                // and
//                // eventually do the actual animation. Also sets it to not loop
//                controller.setAnimation(new AnimationBuilder().addAnimation("scythe.harvest", false));
//            }
//        }
//    }
}
