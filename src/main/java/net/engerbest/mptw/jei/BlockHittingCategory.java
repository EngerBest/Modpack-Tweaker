package net.engerbest.mptw.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.engerbest.mptw.Mptw;
import net.engerbest.mptw.recipe.BlockHittingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("removal")
public class BlockHittingCategory implements IRecipeCategory<BlockHittingRecipe> {
    public static final ResourceLocation UID = ResourceLocation.tryBuild("mptw", "block_hitting");
    public static final RecipeType<BlockHittingRecipe> TYPE = new RecipeType<>(UID, BlockHittingRecipe.class);

    private static final ResourceLocation FURNACE_TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;
    private final IDrawable slotDrawable;

    private final IDrawableStatic vanillaArrow;

    public BlockHittingCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(120, 45);
        this.icon = helper.createDrawableItemStack(new ItemStack(Items.FLINT));
        this.localizedName = Component.translatable("jei.mptw.block_hitting");
        this.slotDrawable = helper.getSlotDrawable();

        this.vanillaArrow = helper.createDrawable(FURNACE_TEXTURE, 79, 34, 24, 17);
    }

    @Override
    public RecipeType<BlockHittingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return this.localizedName;
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BlockHittingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.CATALYST, 5, 15)
                .addIngredients(recipe.getTool())
                .setBackground(this.slotDrawable, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 40, 15)
                .addIngredients(recipe.getBlockIngredient())
                .setBackground(this.slotDrawable, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 99, 15)
                .addItemStack(recipe.getResultItem(RegistryAccess.EMPTY))
                .setBackground(this.slotDrawable, -1, -1);
    }

    @Override
    public void draw(BlockHittingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics,
                     double mouseX, double mouseY) {
        var font = Minecraft.getInstance().font;

        this.vanillaArrow.draw(guiGraphics, 65, 15);

        int plusColor = 0xFF8B8B8B;
        guiGraphics.fill(25, 22, 36, 25, plusColor);
        guiGraphics.fill(29, 18, 32, 29, plusColor);

        if (mouseX >= 25 && mouseX <= 35 && mouseY >= 18 && mouseY <= 29) {
            var plusTooltip = Component.translatable("jei.mptw.block_hitting.plus_tooltip");

            guiGraphics.renderTooltip(font, plusTooltip, (int) mouseX, (int) mouseY);
        }

        if (mouseX >= 66 && mouseX <= 87 && mouseY >= 16 && mouseY <= 31) {
            int percent = (int) (recipe.getChance() * 100);

            var arrowTooltip = Component.translatable("jei.mptw.block_hitting.arrow_tooltip", percent);

            guiGraphics.renderTooltip(font, arrowTooltip, (int) mouseX, (int) mouseY);
        }
    }
}
