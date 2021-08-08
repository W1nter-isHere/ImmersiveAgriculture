package wintersteve25.immersiveagriculture.client.renderers;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.block.sprinkler.SprinklerBlockItem;

public class SprinklerBlockItemRenderer extends GeoItemRenderer<SprinklerBlockItem> {
    public SprinklerBlockItemRenderer() {
        super(new SprinklerBlockItemModel());
    }

    public static class SprinklerBlockItemModel extends AnimatedGeoModel<SprinklerBlockItem> {
        @Override
        public ResourceLocation getModelLocation(SprinklerBlockItem sprinklerBlockItem) {
            return new ResourceLocation(ImmersiveAgriculture.MODID, "geo/sprinkler.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(SprinklerBlockItem sprinklerBlockItem) {
            return new ResourceLocation(ImmersiveAgriculture.MODID, "textures/block/sprinkler.png");
        }

        @Override
        public ResourceLocation getAnimationFileLocation(SprinklerBlockItem sprinklerBlockItem) {
            return new ResourceLocation(ImmersiveAgriculture.MODID, "animations/sprinkler.animation.json");
        }
    }
}
