package wintersteve25.immersiveagriculture.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import wintersteve25.immersiveagriculture.common.init.IAEnchantments;

public interface IFCEnchantment {
    String regName();

    Enchantment og();

    default void init() {
        IAEnchantments.EnchantList.add(this);
    }
}
