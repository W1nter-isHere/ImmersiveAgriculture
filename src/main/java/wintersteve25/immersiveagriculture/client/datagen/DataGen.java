package wintersteve25.immersiveagriculture.client.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;

@Mod.EventBusSubscriber(modid = ImmersiveAgriculture.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen{

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        ForgeBlockTagsProvider blockTags = new ForgeBlockTagsProvider(gen, existingFileHelper);

        if (event.includeServer()) {
            gen.addProvider(new RecipeGen(gen));
            gen.addProvider(new LootTableGen(gen));
            gen.addProvider(new TagGen(gen, blockTags, existingFileHelper));
        }

        if (event.includeClient()) {
            gen.addProvider(new StateGen(gen, existingFileHelper));
            gen.addProvider(new ModelGen(gen, existingFileHelper));

            //en_US
            gen.addProvider(new LangGen.English(gen));
        }
    }

}
