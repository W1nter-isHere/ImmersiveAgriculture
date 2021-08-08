package wintersteve25.immersiveagriculture.client.renderers;

import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.items.ScytheItem;

public class ScytheRenderer extends GeoItemRenderer<ScytheItem> {
    public ScytheRenderer() {
        super(new ScytheModel());
    }

    static class ScytheModel extends AnimatedGeoModel<ScytheItem> {
        @Override
        public ResourceLocation getModelLocation(ScytheItem scytheItem) {
            return new ResourceLocation(ImmersiveAgriculture.MODID, "geo/scythe.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(ScytheItem scytheItem) {
            switch ((ItemTier) scytheItem.getTier()) {
                case WOOD:
                    return new ResourceLocation(ImmersiveAgriculture.MODID, "textures/item/wooden_scythe.png");
                case STONE:
                    return new ResourceLocation(ImmersiveAgriculture.MODID, "textures/item/stone_scythe.png");
                case GOLD:
                    return new ResourceLocation(ImmersiveAgriculture.MODID, "textures/item/golden_scythe.png");
                case IRON:
                    return new ResourceLocation(ImmersiveAgriculture.MODID, "textures/item/iron_scythe.png");
                case DIAMOND:
                    return new ResourceLocation(ImmersiveAgriculture.MODID, "textures/item/diamond_scythe.png");
                default:
                    return new ResourceLocation(ImmersiveAgriculture.MODID, "textures/item/netherite_scythe.png");
            }
        }

        @Override
        public ResourceLocation getAnimationFileLocation(ScytheItem scytheItem) {
            return new ResourceLocation(ImmersiveAgriculture.MODID, "animations/scythe.animation.json");
        }
    }
}
