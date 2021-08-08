package wintersteve25.immersiveagriculture.client.renderers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.block.sprinkler.SprinklerTE;

public class SprinklerRenderer extends GeoBlockRenderer<SprinklerTE> {
    public SprinklerRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new SprinklerModel());
    }

    @Override
    public RenderType getRenderType(SprinklerTE animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityTranslucent(getTextureLocation(animatable));
    }

    public static class SprinklerModel extends AnimatedGeoModel<SprinklerTE> {
        @Override
        public ResourceLocation getModelLocation(SprinklerTE sprinklerBlockItem) {
            return new ResourceLocation(ImmersiveAgriculture.MODID, "geo/sprinkler.geo.json");
        }

        @Override
        public ResourceLocation getTextureLocation(SprinklerTE sprinklerBlockItem) {
            return new ResourceLocation(ImmersiveAgriculture.MODID, "textures/block/sprinkler.png");
        }

        @Override
        public ResourceLocation getAnimationFileLocation(SprinklerTE sprinklerBlockItem) {
            return new ResourceLocation(ImmersiveAgriculture.MODID, "animations/sprinkler.animation.json");
        }
    }
}
