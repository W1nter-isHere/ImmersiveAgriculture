package wintersteve25.immersiveagriculture.client.renderers;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.entities.FertilizerCartEntity;

public class FertilizerCartRenderer extends GeoEntityRenderer<FertilizerCartEntity> {
    public FertilizerCartRenderer(EntityRendererManager renderManager) {
        super(renderManager, new FertilizerCartModel());
    }

    @Override
    public ResourceLocation getEntityTexture(FertilizerCartEntity entity) {
        return new ResourceLocation(ImmersiveAgriculture.MODID, "textures/entities/fertilizer_cart.png");
    }

    public static class FertilizerCartModel extends AnimatedGeoModel<FertilizerCartEntity> {
        @Override
        public ResourceLocation getModelLocation(FertilizerCartEntity fertilizerCartEntity) {
            World world = fertilizerCartEntity.world;
            int i = fertilizerCartEntity.getFertilizerLevel();
            if (i > 16 && i < 33) {
                return new ResourceLocation(ImmersiveAgriculture.MODID, "geo/fertilizer_cart_one.geo.json");
            }
            if (i > 32) {
                return new ResourceLocation(ImmersiveAgriculture.MODID, "geo/fertilizer_cart_two.geo.json");
            }
            return new ResourceLocation(ImmersiveAgriculture.MODID, "geo/fertilizer_cart.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(FertilizerCartEntity fertilizerCartEntity) {
            return new ResourceLocation(ImmersiveAgriculture.MODID, "textures/entities/fertilizer_cart.png");
        }

        @Override
        public ResourceLocation getAnimationFileLocation(FertilizerCartEntity fertilizerCartEntity) {
            return new ResourceLocation(ImmersiveAgriculture.MODID, "animations/fertilizer_cart.animation.json");
        }
    }
}
