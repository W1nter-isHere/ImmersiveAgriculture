package wintersteve25.immersiveagriculture.common.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.items.*;
import wintersteve25.immersiveagriculture.common.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class IAItems {
    public static final ScytheItem WOODEN_SCYTHE = new ScytheItem(ItemTier.WOOD, -3.2F, 6, true);
    public static final ScytheItem STONE_SCYTHE = new ScytheItem(ItemTier.STONE, -3.2F, 6, false);
    public static final ScytheItem IRON_SCYTHE = new ScytheItem(ItemTier.IRON, -3.2F, 6, false);
    public static final ScytheItem GOLD_SCYTHE = new ScytheItem(ItemTier.GOLD, -3.2F, 6, false);
    public static final ScytheItem DIAMOND_SCYTHE = new ScytheItem(ItemTier.DIAMOND, -3.2F, 6, false);
    public static final ScytheItem NETHERITE_SCYTHE = new ScytheItem(ItemTier.NETHERITE, -3.2F, 6, false);
    public static final WateringCanItem WATERING_CAN_ITEM = new WateringCanItem();

    public static final FertilizerItem FERTILIZER = new FertilizerItem(new Item.Properties().group(ImmersiveAgriculture.creativeTab), "Fertilizer");
    public static final BasicItem WOODEN_MESH = new BasicItem(new Item.Properties().group(ImmersiveAgriculture.creativeTab), "Wooden Mesh");
    public static final BasicItem MOLDED_WHEAT = new BasicItem(new Item.Properties().food(IAFoods.BAD_WHEAT).group(ImmersiveAgriculture.creativeTab), "Moldy Wheat");
    public static final BasicItem MOLDED_BREAD = new BasicItem(new Item.Properties().food(IAFoods.BAD_BREAD).group(ImmersiveAgriculture.creativeTab), "Moldy Bread");

    public static final List<IFCItem> ItemList = new ArrayList<>();

    public static void register() {
        WOODEN_SCYTHE.initItem();
        STONE_SCYTHE.initItem();
        IRON_SCYTHE.initItem();
        GOLD_SCYTHE.initItem();
        DIAMOND_SCYTHE.initItem();
        NETHERITE_SCYTHE.initItem();
        WATERING_CAN_ITEM.initItem();
        MOLDED_WHEAT.initItem();
        MOLDED_BREAD.initItem();
        FERTILIZER.initItem();
        WOODEN_MESH.initItem();
    }
}
