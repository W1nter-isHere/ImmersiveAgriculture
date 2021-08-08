package wintersteve25.immersiveagriculture.common.block.hydroponic_tile;

import fictioncraft.wintersteve25.fclib.common.base.FCLibBlockItem;
import fictioncraft.wintersteve25.fclib.common.interfaces.IColoredName;
import fictioncraft.wintersteve25.fclib.common.interfaces.IHasToolTip;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.init.IABlocks;

import java.util.ArrayList;
import java.util.List;

public class HydroponicTileBlockItem extends FCLibBlockItem implements IHasToolTip, IColoredName {
    public HydroponicTileBlockItem() {
        super(IABlocks.HYDROPONIC_TILE, new Item.Properties().group(ImmersiveAgriculture.creativeTab));
    }

    @Override
    public TextFormatting color() {
        return TextFormatting.DARK_GREEN;
    }

    @Override
    public List<ITextComponent> tooltip() {
        List<ITextComponent> list = new ArrayList<>();
        list.add(new TranslationTextComponent("immersiveagriculture.tooltips.hydroponic_tile"));
        return list;
    }
}
