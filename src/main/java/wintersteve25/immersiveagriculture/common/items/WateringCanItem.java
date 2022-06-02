package wintersteve25.immersiveagriculture.common.items;

import fictioncraft.wintersteve25.fclib.common.interfaces.IHasToolTip;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import wintersteve25.immersiveagriculture.common.init.IAItems;
import wintersteve25.immersiveagriculture.common.utils.Utils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WateringCanItem extends ItemFluidContainer implements IFCItem, IHasToolTip {
    private final int capacity;
    private final boolean adv;

    public WateringCanItem(int capacity, boolean advanced) {
        super(Utils.BaseItemProperties.maxDamage(4000), capacity);
        this.capacity = capacity;
        this.adv = advanced;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public int getDamage(ItemStack stack) {
        AtomicInteger damage = new AtomicInteger();
        if (stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
            FluidUtil.getFluidContained(stack).ifPresent(fluid -> damage.set(capacity - fluid.getAmount()));
            return damage.get();
        }
        return 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BlockRayTraceResult pos = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        BlockState state = worldIn.getBlockState(pos.getPos());
        ItemStack wateringCan = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote()) {
            if (playerIn.isSneaking()) {
                if (state.getMaterial() == Material.WATER) {
                    if (FluidUtil.getFluidContained(wateringCan).isPresent()) {
                        if (FluidUtil.getFluidContained(wateringCan).get().getAmount() + 1000 > capacity) {
                            return ActionResult.resultPass(wateringCan);
                        }
                    }

                    FluidActionResult result;
                    result = (FluidUtil.tryPickUpFluid(wateringCan, playerIn, worldIn, pos.getPos(), pos.getFace()));
                    worldIn.playSound((PlayerEntity) null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

                    return ActionResult.resultSuccess(result.getResult());
                }
            } else if (!playerIn.isSneaking()) {
                if (!state.isAir()) {
                    return ActionResult.resultSuccess(activate(wateringCan, worldIn, playerIn, pos.getPos()));
                }
            }
        }

        return ActionResult.resultPass(wateringCan);
    }

    public ItemStack activate(ItemStack itemStack, World world, @Nullable PlayerEntity player, BlockPos pos) {
        ItemStack result = itemStack;

        if (FluidUtil.getFluidContained(itemStack).isPresent()) {
            FluidStack fluid = FluidUtil.getFluidContained(itemStack).get();

            if (fluid.getAmount() > 100) {
                Utils.water(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3);
                if (FluidUtil.getFluidHandler(result).resolve().isPresent()) {
                    FluidUtil.getFluidHandler(result).resolve().get().drain(100, IFluidHandler.FluidAction.EXECUTE);
                }
                world.playSound((PlayerEntity) null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
            }
        }

        return result;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group) && !adv) {
            items.add(new ItemStack(IAItems.WATERING_CAN_ITEM));
            items.add(new ItemStack(IAItems.WATERING_CAN_ITEM_ADVANCED));
        }
    }

    @Override
    public String regName() {
        return adv ? "Advanced Watering Can" : "Watering Can";
    }

    @Override
    public Item ogItem() {
        return this;
    }

    @Override
    public List<ITextComponent> tooltip() {
        List<ITextComponent> list = new ArrayList<>();
        list.add(new TranslationTextComponent("immersiveagriculture.tooltips.watering_can"));
        return list;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.addAll(this.tooltip());
        } else {
            tooltip.add(new TranslationTextComponent("fclib.shiftInfo"));
        }
    }
}
