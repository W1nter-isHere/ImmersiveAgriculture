package wintersteve25.immersiveagriculture.common.items;

import net.minecraft.item.Item;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.init.IAItems;

//TODO: use FCLib IFCLibItem instead
public interface IFCItem {
    String regName();

    Item ogItem();

    default void initItem() {
        IAItems.ItemList.add(this);
    }
}
