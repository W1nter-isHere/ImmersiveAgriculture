package wintersteve25.immersiveagriculture.client.datagen;

import fictioncraft.wintersteve25.fclib.common.helper.MiscHelper;
import fictioncraft.wintersteve25.fclib.common.interfaces.IFCDataGenObject;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.data.LanguageProvider;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.config.IAConfigs;
import wintersteve25.immersiveagriculture.common.enchantment.IFCEnchantment;
import wintersteve25.immersiveagriculture.common.init.IABlocks;
import wintersteve25.immersiveagriculture.common.init.IAEnchantments;
import wintersteve25.immersiveagriculture.common.items.IFCItem;
import wintersteve25.immersiveagriculture.common.init.IAItems;

public class LangGen {
    public static class English extends LanguageProvider {
        public English(DataGenerator gen) {
            super(gen, ImmersiveAgriculture.MODID, "en_us");
        }

        @Override
        protected void addTranslations() {
            autoGenLang();
            add("itemGroup.immersiveagriculture", "Immersive Agriculture");
            add("enchantment.immersiveagriculture.farmer_s_luck.desc", "Provides a better yield from harvesting crops");
            add("immersiveagriculture.farmtile.reachlimit", "Fertilizer Limit Reached!");
            add("immersiveagriculture.hydroponic_tile", "Hydroponic Tile Contains: %smb of %s");
            add("immersiveagriculture.sprinkler", "Sprinkler Contains: %smb of %s");
            add("immersiveagriculture.reload", "Config file reloaded");
            add("immersiveagriculture.jei.crop", "Crop Growth Requirements");
            add("immersiveagriculture.jei.crop.fertilizerAmount", "Require at least %s Fertilizers");
            add("immersiveagriculture.jei.crop.requiredFluid", "Require at least %smb of %s");
            add("immersiveagriculture.jei.crop.consumeFluid", "Consumes required fluid");
            add("immersiveagriculture.tooltips.fertilizer", TextFormatting.GRAY + "Fertilizer can be used on \"Farm Tiles\" and \"Hydroponic Tiles\" to increase fertilization level in the tiles \nIt can also be used in the fertilization station to automatically fertilize the tiles");
            add("immersiveagriculture.tooltips.watering_can", TextFormatting.GRAY + "Watering can be filled by shift-right click water source blocks. When used around farmland blocks it will moisture the block and boost crop growth speed for a tiny bit");
            add("immersiveagriculture.tooltips.farm_tile", TextFormatting.GRAY + "An upgraded version of the vanilla Farmland. Enables fertilizers. Right click fertilizers to increase the fertilization level of the tile");
            add("immersiveagriculture.tooltips.hydroponic_tile", TextFormatting.GRAY + "An upgraded version of Farm Tiles, allow player to put fluids inside the tile in order to meet the requirement of some crops. Shift right-click the block to check contained fluids");
            add("immersiveagriculture.tooltips.sprinkler", TextFormatting.GRAY + "The Sprinkler works in a " + IAConfigs.SPRINKLER_RANGE.get()+ "x" + IAConfigs.SPRINKLER_RANGE.get() + " range, moistures farmlands in range when provided water and power. Shift right-click the block to check contained fluids");
        }
        private void autoGenLang() {
            for (IFCItem item : IAItems.ItemList) {
                add("item.immersiveagriculture." + MiscHelper.langToReg(item.regName()), item.regName());
            }
            for (IFCEnchantment enchantment : IAEnchantments.EnchantList) {
                add("enchantment.immersiveagriculture." + MiscHelper.langToReg(enchantment.regName()), enchantment.regName());
            }
            for (IFCDataGenObject<Block> block : IABlocks.BlockList) {
                add("block.immersiveagriculture." + MiscHelper.langToReg(block.regName()), block.regName());
            }
            for (IFCDataGenObject<Block> block : IABlocks.BlockMap.keySet()) {
                add("block.immersiveagriculture." + MiscHelper.langToReg(block.regName()), block.regName());
            }
        }
    }
}
