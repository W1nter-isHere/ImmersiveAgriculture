package wintersteve25.immersiveagriculture.client.datagen;

import fictioncraft.wintersteve25.fclib.client.datagen.LootTableBase;
import fictioncraft.wintersteve25.fclib.common.interfaces.IFCDataGenObject;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import wintersteve25.immersiveagriculture.common.init.IABlocks;

public class LootTableGen extends LootTableBase {
    public LootTableGen(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        autoGen();
    }

    private void autoGen() {
        for (IFCDataGenObject<Block> b : IABlocks.BlockList) {
            lootTables.putIfAbsent(b.getOg(), createStandardTable(b.regName(), b.getOg()));
        }
        for (IFCDataGenObject<Block> b : IABlocks.BlockMap.keySet()) {
            lootTables.putIfAbsent(b.getOg(), createStandardTable(b.regName(), b.getOg()));
        }
    }
}
