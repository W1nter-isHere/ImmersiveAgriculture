package wintersteve25.immersiveagriculture.client.datagen;

import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import wintersteve25.immersiveagriculture.common.init.IABlocks;
import wintersteve25.immersiveagriculture.common.init.IAItems;
import wintersteve25.immersiveagriculture.common.init.IATags;

import java.util.function.Consumer;

public class RecipeGen extends RecipeProvider {
    public RecipeGen(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        super.registerRecipes(consumer);
        scytheRecipe(IAItems.WOODEN_SCYTHE, ItemTags.PLANKS, consumer);
        scytheRecipe(IAItems.STONE_SCYTHE, ItemTags.STONE_TOOL_MATERIALS, consumer);
        scytheRecipe(IAItems.GOLD_SCYTHE, Tags.Items.INGOTS_GOLD, consumer);
        scytheRecipe(IAItems.IRON_SCYTHE, Tags.Items.INGOTS_IRON, consumer);
        scytheRecipe(IAItems.DIAMOND_SCYTHE, Tags.Items.GEMS_DIAMOND, consumer);
        smithingRecipe(IAItems.DIAMOND_SCYTHE, IAItems.NETHERITE_SCYTHE, consumer);
        ShapedRecipeBuilder.shapedRecipe(IAItems.FERTILIZER, 8).key('S', Tags.Items.SAND_COLORLESS).key('B', IATags.MOLD_CROPS).patternLine("SBS").addCriterion("has_bonemeal", hasItem(Items.BONE_MEAL)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(IABlocks.FARM_TILE, 4).key('I', Tags.Items.INGOTS_IRON).key('F', IAItems.FERTILIZER).key('C', IAItems.WOODEN_MESH).key('D', Items.DIAMOND).patternLine("ICI").patternLine("FDF").patternLine("ICI").addCriterion("has_diamond", hasItem(Items.DIAMOND)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(IABlocks.HYDROPONIC_TILE, 2).key('I', Tags.Items.INGOTS_GOLD).key('F', IABlocks.FARM_TILE).key('O', IAItems.WOODEN_MESH).key('D', Items.DIAMOND).patternLine("IOI").patternLine("DFD").patternLine("IOI").addCriterion("has_farmtile", hasItem(IABlocks.FARM_TILE)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(IAItems.WATERING_CAN_ITEM, 1).key('I', Tags.Items.INGOTS_IRON).key('F', IAItems.FERTILIZER).key('C', Tags.Items.DYES_GREEN).patternLine("  I").patternLine("IFI").patternLine("CIC").addCriterion("has_fertilizer", hasItem(IAItems.FERTILIZER)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(IAItems.WOODEN_MESH).key('S', Tags.Items.RODS_WOODEN).patternLine("SSS").patternLine("SSS").patternLine("SSS").addCriterion("has_stick", hasItem(Tags.Items.RODS_WOODEN)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(IAItems.MOLDED_BREAD).key('W', IAItems.MOLDED_WHEAT).patternLine("WWW").addCriterion("has_mold_wheat", hasItem(IAItems.MOLDED_WHEAT)).build(consumer);
    }

    private void scytheRecipe(IItemProvider scythe, ITag<Item> material, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(scythe).key('M', material).key('S', Tags.Items.RODS_WOODEN).patternLine("MMM").patternLine("M S").patternLine("  S").addCriterion("has_planks", hasItem(ItemTags.PLANKS)).build(consumer);
    }

    private void smithingRecipe(IItemProvider input, Item output, Consumer<IFinishedRecipe> consumer) {
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(input), Ingredient.fromItems(Items.NETHERITE_INGOT), output).addCriterion("has_netherite_ingot", hasItem(Items.NETHERITE_INGOT)).build(consumer, Registry.ITEM.getKey(output.asItem()));
    }
}
