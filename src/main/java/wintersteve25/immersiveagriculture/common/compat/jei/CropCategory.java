package wintersteve25.immersiveagriculture.common.compat.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import fictioncraft.wintersteve25.fclib.client.renderer.RenderingHelper;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.config.CropRecipe;
import wintersteve25.immersiveagriculture.common.init.IABlocks;

public class CropCategory implements IRecipeCategory<CropRecipe> {

    private IDrawable background;
    private IDrawable tabIcon;
    private IDrawable hydroponicTile = new IDrawable() {
        @Override
        public int getWidth() {
            return 50;
        }

        @Override
        public int getHeight() {
            return 50;
        }

        @Override
        public void draw(MatrixStack matrixStack, int i, int i1) {
            RenderingHelper.renderItemStackInGui(matrixStack, i, i1, new ItemStack(IABlocks.HYDROPONIC_TILE), false);
        }
    };

    private IDrawable farmTile = new IDrawable() {
        @Override
        public int getWidth() {
            return 50;
        }

        @Override
        public int getHeight() {
            return 50;
        }

        @Override
        public void draw(MatrixStack matrixStack, int i, int i1) {
            RenderingHelper.renderItemStackInGui(matrixStack, i, i1, new ItemStack(IABlocks.FARM_TILE), false);
        }
    };

    private final IGuiHelper iGuiHelper;

    public CropCategory(IGuiHelper iGuiHelper) {
        background = iGuiHelper.createDrawable(new ResourceLocation(ImmersiveAgriculture.MODID, "textures/gui/crop_jei_empty.png"), 0, 0, 170, 110);
        tabIcon = iGuiHelper.createDrawableIngredient(new ItemStack(Items.WHEAT_SEEDS));

        this.iGuiHelper = iGuiHelper;
    }

    @Override
    public ResourceLocation getUid() {
        return IAJEIPlugin.CROP_UID;
    }

    @Override
    public Class<? extends CropRecipe> getRecipeClass() {
        return CropRecipe.class;
    }

    @Override
    public String getTitle() {
        return I18n.format("immersiveagriculture.jei.crop");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return tabIcon;
    }

    @Override
    public void setIngredients(CropRecipe cropRecipe, IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.ITEM, cropRecipe.getSeedItem());
        iIngredients.setInput(VanillaTypes.FLUID, cropRecipe.getRequiredFluid());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, CropRecipe cropRecipe, IIngredients iIngredients) {
        IGuiItemStackGroup itemStacks = iRecipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = iRecipeLayout.getFluidStacks();

        itemStacks.init(0, true, 75, 8);
        itemStacks.set(0, cropRecipe.getSeedItem());

        fluidStacks.init(0, true, 76, 78);
        fluidStacks.set(0, cropRecipe.getRequiredFluid());
    }

    @Override
    public void draw(CropRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        if (recipe.isRequireHighTier()) {
            hydroponicTile.draw(matrixStack, 76, 29);
        } else {
            farmTile.draw(matrixStack, 76, 29);
        }

        Minecraft.getInstance().fontRenderer.func_243246_a(matrixStack, new TranslationTextComponent("immersiveagriculture.jei.crop.fertilizerAmount", recipe.getFertilizerAmount()), 0, 100, TextFormatting.WHITE.getColor());

        if (recipe.getRequiredFluid() != null) {
            if (recipe.isConsumeFluid()) {
                background = iGuiHelper.createDrawable(new ResourceLocation(ImmersiveAgriculture.MODID, "textures/gui/crop_jei.png"), 0, 0, 170, 131);
                Minecraft.getInstance().fontRenderer.func_243246_a(matrixStack, new TranslationTextComponent("immersiveagriculture.jei.crop.consumeFluid"), 0, 120, TextFormatting.WHITE.getColor());
            } else {
                background = iGuiHelper.createDrawable(new ResourceLocation(ImmersiveAgriculture.MODID, "textures/gui/crop_jei.png"), 0, 0, 170, 121);
            }
            Minecraft.getInstance().fontRenderer.func_243246_a(matrixStack, new TranslationTextComponent("immersiveagriculture.jei.crop.requiredFluid", recipe.getRequiredFluid().getAmount(), recipe.getRequiredFluid().getDisplayName()), 0, 110, TextFormatting.WHITE.getColor());
        }
    }
}
