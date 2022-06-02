package wintersteve25.immersiveagriculture.common.init;

import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import wintersteve25.immersiveagriculture.common.entities.FertilizerCartEntity;
import wintersteve25.immersiveagriculture.common.utils.RegistryHelper;

public class IAEntities {
    public static final RegistryObject<EntityType<FertilizerCartEntity>> FERTILIZER_CART = RegistryHelper.registerEntity("fertilizer_cart", FertilizerCartEntity::new, 0.8f, 1f);

    public static void register() {
    }
}