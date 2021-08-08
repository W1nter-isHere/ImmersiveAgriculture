package wintersteve25.immersiveagriculture.common.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import wintersteve25.immersiveagriculture.common.enchantment.BaseEnchantment;
import wintersteve25.immersiveagriculture.common.enchantment.IFCEnchantment;
import wintersteve25.immersiveagriculture.common.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class IAEnchantments {
    public static final BaseEnchantment FARMER_LUCK = new BaseEnchantment(Enchantment.Rarity.UNCOMMON, Utils.SCYTHE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND}, "Farmer's Luck");

    public static final List<IFCEnchantment> EnchantList = new ArrayList<>();

    public static void register() {
        FARMER_LUCK.init();
    }
}
