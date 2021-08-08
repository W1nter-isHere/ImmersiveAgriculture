package wintersteve25.immersiveagriculture.common.init;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;

public class IATags {
    public static Tags.IOptionalNamedTag<Item> MOLD_CROPS = tag("mold_crops");

    private static Tags.IOptionalNamedTag<Item> tag(String name) {
        return ItemTags.createOptional(new ResourceLocation(ImmersiveAgriculture.MODID, name));
    }

    public static void register(){}
}
