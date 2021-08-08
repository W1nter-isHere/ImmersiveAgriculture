package wintersteve25.immersiveagriculture.common.init;

import fictioncraft.wintersteve25.fclib.common.base.FCLibBlockItem;
import fictioncraft.wintersteve25.fclib.common.helper.MiscHelper;
import fictioncraft.wintersteve25.fclib.common.interfaces.IFCDataGenObject;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import wintersteve25.immersiveagriculture.common.block.farm_tile.FarmTileBlock;
import wintersteve25.immersiveagriculture.common.block.farm_tile.FarmTileBlockItem;
import wintersteve25.immersiveagriculture.common.block.hydroponic_tile.HydroponicTileBlock;
import wintersteve25.immersiveagriculture.common.block.hydroponic_tile.HydroponicTileBlockItem;
import wintersteve25.immersiveagriculture.common.block.hydroponic_tile.HydroponicTileTE;
import wintersteve25.immersiveagriculture.common.block.sprinkler.SprinklerBlock;
import wintersteve25.immersiveagriculture.common.block.sprinkler.SprinklerBlockItem;
import wintersteve25.immersiveagriculture.common.block.sprinkler.SprinklerTE;
import wintersteve25.immersiveagriculture.common.utils.RegistryHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IABlocks {
    public static final FarmTileBlock FARM_TILE = new FarmTileBlock(AbstractBlock.Properties.create(Material.EARTH).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(0.8F, 1F).tickRandomly(), false);
    public static final FarmTileBlockItem FARM_TILE_BLOCK_ITEM = new FarmTileBlockItem();
    public static final HydroponicTileBlock HYDROPONIC_TILE = new HydroponicTileBlock(AbstractBlock.Properties.create(Material.EARTH).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(1F, 1.5F).tickRandomly());
    public static final HydroponicTileBlockItem HYDROPONIC_TILE_BLOCK_ITEM = new HydroponicTileBlockItem();
    public static final RegistryObject<TileEntityType<HydroponicTileTE>> HYDROPONIC_TILE_TE = RegistryHelper.registerTE(MiscHelper.langToReg(HYDROPONIC_TILE.regName()), () -> TileEntityType.Builder.create(HydroponicTileTE::new, HYDROPONIC_TILE).build(null));
    public static final SprinklerBlock SPRINKLER_BLOCK = new SprinklerBlock(AbstractBlock.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2F, 4F).setRequiresTool().notSolid());
    public static final SprinklerBlockItem SPRINKLER_BLOCK_ITEM = new SprinklerBlockItem();
    public static final RegistryObject<TileEntityType<SprinklerTE>> SPRINKLER_TE = RegistryHelper.registerTE(MiscHelper.langToReg(SPRINKLER_BLOCK.regName()), () -> TileEntityType.Builder.create(SprinklerTE::new, SPRINKLER_BLOCK).build(null));

    public static final List<IFCDataGenObject<Block>> BlockList = new ArrayList<>();
    public static final Map<IFCDataGenObject<Block>, FCLibBlockItem> BlockMap = new HashMap<>();

    public static void register() {
        FARM_TILE.init(BlockMap, FARM_TILE_BLOCK_ITEM);
        HYDROPONIC_TILE.init(BlockMap, HYDROPONIC_TILE_BLOCK_ITEM);
        SPRINKLER_BLOCK.init(BlockMap, SPRINKLER_BLOCK_ITEM);
    }
}
