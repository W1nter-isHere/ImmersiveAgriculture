package wintersteve25.immersiveagriculture.common.items;

import fictioncraft.wintersteve25.fclib.common.interfaces.IHasToolTip;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class FertilizerItem extends BasicItem implements IHasToolTip {
    public FertilizerItem(Properties properties, String regName) {
        super(properties, regName);
    }

    @Override
    public List<ITextComponent> tooltip() {
        List<ITextComponent> list = new ArrayList<>();
        list.add(new TranslationTextComponent("immersiveagriculture.tooltips.fertilizer"));
        return list;
    }
}
