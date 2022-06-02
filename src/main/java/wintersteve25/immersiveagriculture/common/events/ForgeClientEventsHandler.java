package wintersteve25.immersiveagriculture.common.events;

import com.mrcrayfish.obfuscate.client.event.PlayerModelEvent;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.entities.FertilizerCartEntity;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = ImmersiveAgriculture.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeClientEventsHandler {
    @SubscribeEvent
    public static void renderEvent(PlayerModelEvent.SetupAngles.Post event) {
        PlayerEntity player = event.getPlayer();
        if (player.getRidingEntity() instanceof FertilizerCartEntity) {
            PlayerModel model = event.getModelPlayer();
            model.bipedLeftArm.rotateAngleX = 68;
            model.bipedLeftArmwear.rotateAngleX = 68;

            model.bipedRightArm.rotateAngleX = 68;
            model.bipedRightArmwear.rotateAngleX = 68;
        }
    }
}
