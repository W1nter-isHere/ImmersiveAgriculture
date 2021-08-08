package wintersteve25.immersiveagriculture.common.items;

import fictioncraft.wintersteve25.fclib.common.interfaces.IColoredName;
import fictioncraft.wintersteve25.fclib.common.interfaces.IHasToolTip;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BasicItem extends Item implements IFCItem {

    private final String regname;

    public BasicItem(Properties properties, String regName) {
        super(properties);

        this.regname = regName;
    }

    @Override
    public String regName() {
        return regname;
    }

    @Override
    public Item ogItem() {
        return this;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (this instanceof IColoredName) {
            IColoredName coloredName = (IColoredName) this;
            return super.getDisplayName(stack).copyRaw().mergeStyle(coloredName.color());
        }
        return super.getDisplayName(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (this instanceof IHasToolTip) {
            if (Screen.hasShiftDown()) {
                IHasToolTip toolTipBlock = (IHasToolTip) this;
                tooltip.addAll(toolTipBlock.tooltip());
            } else {
                tooltip.add(new TranslationTextComponent("fclib.shiftInfo"));
            }
        }
    }
}
