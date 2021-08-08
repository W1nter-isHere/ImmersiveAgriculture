package wintersteve25.immersiveagriculture.common.config;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Level;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CropRecipe {

    private final ItemStack seedItem;
    private final Block cropBlockItem;
    private final int fertilizerAmount;
    private final boolean requireHighTier;
    private final FluidStack requiredFluid;
    private final boolean consumeFluid;

    public static List<CropRecipe> recipes = new ArrayList<>();

    public CropRecipe(ItemStack seedItem, Block cropBlockItem, int fertilizerAmount, boolean requireHighTier, @Nullable Fluid requiredFluid, int requiredFluidAmount, boolean consumeFluid) {
        this.seedItem = seedItem;
        this.cropBlockItem = cropBlockItem;
        this.fertilizerAmount = fertilizerAmount;
        this.requireHighTier = requireHighTier;

        if (requiredFluid != null) {
            this.requiredFluid = new FluidStack(requiredFluid, requiredFluidAmount);
        } else {
            this.requiredFluid = null;
        }

        this.consumeFluid = consumeFluid;
    }

    public static CropRecipe getRecipeFromConfig(ConfigObject.CropConfiguration configIn) {
        String seedRegName = configIn.getSeedName();

        if (seedRegName == null || seedRegName.isEmpty()) {
            ImmersiveAgriculture.LOGGER.log(Level.ERROR, "Seed Item is null!");
            return null;
        }

        ResourceLocation seedRL = new ResourceLocation(seedRegName);
        Item seedItem = ForgeRegistries.ITEMS.getValue(seedRL);

        if (seedItem == null || seedItem == Items.AIR) {
            ImmersiveAgriculture.LOGGER.log(Level.ERROR, "{} does not exist in forge registry!", seedRegName);
            return null;
        }

        String cropRegName = configIn.getCropName();

        if (cropRegName == null || cropRegName.isEmpty()) {
            ImmersiveAgriculture.LOGGER.log(Level.ERROR, "Crop Block Item is null!");
            return null;
        }

        ResourceLocation cropRL = new ResourceLocation(cropRegName);
        Block cropBlock = ForgeRegistries.BLOCKS.getValue(cropRL);

        if (cropBlock == null || cropBlock == Blocks.AIR) {
            ImmersiveAgriculture.LOGGER.log(Level.ERROR, "{} does not exist in forge registry!", seedRegName);
            return null;
        }

        Fluid fluid = null;

        if (configIn.getRequiredFluid() != null && !configIn.getRequiredFluid().isEmpty()) {
            fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(configIn.getRequiredFluid()));
        }

        return new CropRecipe(new ItemStack(seedItem), cropBlock, configIn.getFertilizerAmount(), configIn.isRequireHighTier(), fluid, configIn.getRequiredFluidAmount(), configIn.isConsumeFluid());
    }

    public static boolean addRecipe(CropRecipe recipe) {
        if (recipes.isEmpty()) {
            recipes.add(recipe);
            return true;
        }

        for (CropRecipe cropRecipes : recipes) {
            if (recipe.getSeedItem().getItem() != cropRecipes.getSeedItem().getItem()) {
                recipes.add(recipe);
                return true;
            }
        }

        ImmersiveAgriculture.LOGGER.info("Skipping {} crop recipe as it already exists", recipe.seedItem.getItem().getRegistryName().toString());
        return false;
    }

    public ItemStack getSeedItem() {
        return seedItem;
    }

    public Block getCropBlockItem() {
        return cropBlockItem;
    }

    public int getFertilizerAmount() {
        return fertilizerAmount;
    }

    public boolean isRequireHighTier() {
        return requireHighTier;
    }

    public FluidStack getRequiredFluid() {
        return requiredFluid;
    }

    public boolean isConsumeFluid() {
        return consumeFluid;
    }
}
