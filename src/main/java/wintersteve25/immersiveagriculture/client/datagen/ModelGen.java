package wintersteve25.immersiveagriculture.client.datagen;

import fictioncraft.wintersteve25.fclib.common.helper.MiscHelper;
import fictioncraft.wintersteve25.fclib.common.interfaces.IFCDataGenObject;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import software.bernie.geckolib3.core.IAnimatable;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.init.IABlocks;
import wintersteve25.immersiveagriculture.common.items.IFCItem;
import wintersteve25.immersiveagriculture.common.init.IAItems;

public class ModelGen extends ItemModelProvider {
    public ModelGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ImmersiveAgriculture.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile generated = getExistingFile(mcLoc("item/generated"));

        autoGenModels(generated);
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

    private void autoGenModels(ModelFile parent) {
        for (IFCItem item : IAItems.ItemList) {
            if (!(item.ogItem() instanceof IAnimatable)) {
                builder(parent, MiscHelper.langToReg(item.regName()));
            }
        }
        for (IFCDataGenObject<Block> block : IABlocks.BlockList) {
            if (block.getOg() != null) {
                withExistingParent(MiscHelper.langToReg(block.regName()), modLoc("block/" + MiscHelper.langToReg(block.regName())));
            }
        }
        for (IFCDataGenObject<Block> block : IABlocks.BlockMap.keySet()) {
            if (block.getOg() != null && block.getOg() != IABlocks.SPRINKLER_BLOCK) {
                withExistingParent(MiscHelper.langToReg(block.regName()), modLoc("block/" + MiscHelper.langToReg(block.regName())));
            }
        }
    }
}
