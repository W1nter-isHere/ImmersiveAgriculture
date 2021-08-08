package wintersteve25.immersiveagriculture.client.datagen;

import fictioncraft.wintersteve25.fclib.client.datagen.FCStateProvider;
import fictioncraft.wintersteve25.fclib.common.helper.MiscHelper;
import fictioncraft.wintersteve25.fclib.common.helper.ModelFileHelper;
import fictioncraft.wintersteve25.fclib.common.interfaces.IFCDataGenObject;
import net.minecraft.block.Block;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import software.bernie.geckolib3.core.IAnimatable;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.block.sprinkler.SprinklerBlock;
import wintersteve25.immersiveagriculture.common.init.IABlocks;

public class StateGen extends FCStateProvider {
    public StateGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ImmersiveAgriculture.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        autoGen();
    }

    private void autoGen() {
        for (IFCDataGenObject<Block> fcObject : IABlocks.BlockList) {
            if (!(fcObject.getOg() instanceof IAnimatable)) {
                if (fcObject.getOg() instanceof FarmlandBlock) {
                    farmTileBlock(fcObject.getOg(), fcObject, new ResourceLocation(ImmersiveAgriculture.MODID, "block/" + MiscHelper.langToReg(fcObject.regName())), new ResourceLocation(ImmersiveAgriculture.MODID, "block/top_differ_block"));
                }
            }
        }

        for (IFCDataGenObject<Block> fcObject : IABlocks.BlockMap.keySet()) {
            if (!(fcObject.getOg() instanceof IAnimatable)) {
                if (fcObject.getOg() instanceof FarmlandBlock) {
                    farmTileBlock(fcObject.getOg(), fcObject, new ResourceLocation(ImmersiveAgriculture.MODID, "block/" + MiscHelper.langToReg(fcObject.regName())), new ResourceLocation(ImmersiveAgriculture.MODID, "block/top_differ_block"));
                }
            }
        }
    }

    private void farmTileBlock(Block block, IFCDataGenObject fcObject, ResourceLocation baseName, ResourceLocation parent) {
        getVariantBuilder(block)
                .partialState().with(FarmlandBlock.MOISTURE, 0)
                .modelForState().modelFile(ModelFileHelper.getTopDifferModel(baseName, MiscHelper.langToReg(fcObject.regName()), models(), parent)).addModel()
                .partialState().with(FarmlandBlock.MOISTURE, 1)
                .modelForState().modelFile(ModelFileHelper.getTopDifferModel(baseName, MiscHelper.langToReg(fcObject.regName()), models(), parent)).addModel()
                .partialState().with(FarmlandBlock.MOISTURE, 2)
                .modelForState().modelFile(ModelFileHelper.getTopDifferModel(baseName, MiscHelper.langToReg(fcObject.regName()), models(), parent)).addModel()
                .partialState().with(FarmlandBlock.MOISTURE, 3)
                .modelForState().modelFile(ModelFileHelper.getTopDifferModel(baseName, MiscHelper.langToReg(fcObject.regName()), models(), parent)).addModel()
                .partialState().with(FarmlandBlock.MOISTURE, 4)
                .modelForState().modelFile(ModelFileHelper.getTopDifferModel(baseName, MiscHelper.langToReg(fcObject.regName()), models(), parent)).addModel()
                .partialState().with(FarmlandBlock.MOISTURE, 5)
                .modelForState().modelFile(ModelFileHelper.getTopDifferModel(baseName, MiscHelper.langToReg(fcObject.regName()), models(), parent)).addModel()
                .partialState().with(FarmlandBlock.MOISTURE, 6)
                .modelForState().modelFile(ModelFileHelper.getTopDifferModel(baseName, MiscHelper.langToReg(fcObject.regName()), models(), parent)).addModel()
                .partialState().with(FarmlandBlock.MOISTURE, 7)
                .modelForState().modelFile(ModelFileHelper.getTopMoistModel(baseName, MiscHelper.langToReg(fcObject.regName()) + "_moist", models(), parent)).addModel();
    }
}
