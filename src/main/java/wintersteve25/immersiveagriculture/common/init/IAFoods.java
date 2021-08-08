package wintersteve25.immersiveagriculture.common.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class IAFoods {
    public static Food BAD_WHEAT = (new Food.Builder()).hunger(1).saturation(0.1F).effect(() -> new EffectInstance(Effects.NAUSEA, 200), 85).effect(() -> new EffectInstance(Effects.WEAKNESS, 200), 85).build();
    public static Food BAD_BREAD = (new Food.Builder()).hunger(4).saturation(0.8F).effect(() -> new EffectInstance(Effects.NAUSEA, 80), 45).effect(() -> new EffectInstance(Effects.WEAKNESS, 80), 45).build();
}
