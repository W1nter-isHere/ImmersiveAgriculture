package wintersteve25.immersiveagriculture.common.block.sprinkler;

import fictioncraft.wintersteve25.fclib.common.base.FCLibBlockItem;
import fictioncraft.wintersteve25.fclib.common.interfaces.IHasToolTip;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.client.renderers.SprinklerBlockItemRenderer;
import wintersteve25.immersiveagriculture.common.config.IAConfigs;
import wintersteve25.immersiveagriculture.common.init.IABlocks;

import java.util.ArrayList;
import java.util.List;

public class SprinklerBlockItem extends FCLibBlockItem implements IHasToolTip, IAnimatable {
    public AnimationFactory factory = new AnimationFactory(this);
    public String controllerName = "controller";

    public SprinklerBlockItem() {
        super(IABlocks.SPRINKLER_BLOCK, new Item.Properties().group(ImmersiveAgriculture.creativeTab).setISTER(() -> SprinklerBlockItemRenderer::new));
    }

    @Override
    public List<ITextComponent> tooltip() {
        List<ITextComponent> list = new ArrayList<>();
        list.add(new TranslationTextComponent("immersiveagriculture.tooltips.sprinkler"));
        return list;
    }

    public <P extends BlockItem & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, controllerName, 1, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
