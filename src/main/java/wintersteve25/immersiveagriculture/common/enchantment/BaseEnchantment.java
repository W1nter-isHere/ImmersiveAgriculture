package wintersteve25.immersiveagriculture.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class BaseEnchantment extends Enchantment implements IFCEnchantment {
    public final String name;

    public BaseEnchantment(Rarity rarity, EnchantmentType type, EquipmentSlotType[] slots, String name) {
        super(rarity, type, slots);
        this.name = name;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 0;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    @Override
    public String regName() {
        return name;
    }

    @Override
    public Enchantment og() {
        return this;
    }
}
