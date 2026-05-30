package net.engerbest.mptw.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.engerbest.mptw.recipe.BlockHittingRecipe;
import net.engerbest.mptw.recipe.ModRecipes;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_ID = ResourceLocation.tryBuild("mptw", "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new BlockHittingCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var minecraft = net.minecraft.client.Minecraft.getInstance();
        if (minecraft.getConnection() != null) {
            var recipeManager = minecraft.getConnection().getRecipeManager();

            List<BlockHittingRecipe> recipes = recipeManager
                    .getAllRecipesFor(ModRecipes.BLOCK_HITTING_TYPE.get())
                    .stream()
                    .toList();

            registration.addRecipes(BlockHittingCategory.TYPE, recipes);
        }
    }
}
