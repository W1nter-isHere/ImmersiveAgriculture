package wintersteve25.immersiveagriculture.api.events;

import net.minecraft.item.ItemUseContext;
import net.minecraftforge.common.MinecraftForge;

public class IAHooks {
    public static ScytheHarvestEvent onScytheHarvestCancel(ItemUseContext context) {
        ScytheHarvestEvent event = new ScytheHarvestEvent(context);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
}
