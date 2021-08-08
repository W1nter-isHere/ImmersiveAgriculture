package wintersteve25.immersiveagriculture.common.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.config.CropRecipe;
import wintersteve25.immersiveagriculture.common.init.IABlocks;

@JeiPlugin
public class IAJEIPlugin implements IModPlugin {
    public static final ResourceLocation UID = new ResourceLocation(ImmersiveAgriculture.MODID, "jei_plugin");
    public static final ResourceLocation CROP_UID = new ResourceLocation(ImmersiveAgriculture.MODID, "jei_crop");

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(CropRecipe.recipes, CROP_UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(IABlocks.FARM_TILE), CROP_UID);
        registration.addRecipeCatalyst(new ItemStack(IABlocks.HYDROPONIC_TILE), CROP_UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CropCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }
}
