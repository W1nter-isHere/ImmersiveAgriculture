package wintersteve25.immersiveagriculture.common.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.client.renderers.FertilizerCartRenderer;
import wintersteve25.immersiveagriculture.client.renderers.SprinklerRenderer;
import wintersteve25.immersiveagriculture.common.init.IABlocks;
import wintersteve25.immersiveagriculture.common.init.IAEntities;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = ImmersiveAgriculture.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEventsHandler {

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(IABlocks.SPRINKLER_TE.get(), SprinklerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(IAEntities.FERTILIZER_CART.get(), FertilizerCartRenderer::new);
    }
}