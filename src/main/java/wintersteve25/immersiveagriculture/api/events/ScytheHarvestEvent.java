package wintersteve25.immersiveagriculture.api.events;

import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * This event is fired when player right clicks an instance of CropBlock and right before the crop is harvested. Cancelling the event will stop the crop being harvested
 * You can set the ActionResult to something else as well using setActionResult method
 */
@Cancelable
public class ScytheHarvestEvent extends Event {
    private final ItemUseContext context;
    private ActionResultType result = ActionResultType.SUCCESS;

    public ScytheHarvestEvent(ItemUseContext context) {
        this.context = context;
    }

    public ItemUseContext getContext() {
        return context;
    }

    public ActionResultType getActionResult() {
        return result;
    }

    public void setActionResult(ActionResultType result) {
        this.result = result;
    }
}
