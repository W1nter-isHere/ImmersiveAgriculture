package wintersteve25.immersiveagriculture.client.datagen;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.init.IAItems;
import wintersteve25.immersiveagriculture.common.init.IATags;

public class TagGen extends ItemTagsProvider {
    public TagGen(DataGenerator gen, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(gen, blockTagProvider, ImmersiveAgriculture.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(IATags.MOLD_CROPS).add(IAItems.MOLDED_WHEAT, IAItems.MOLDED_BREAD);
    }
}
