package net.engerbest.mptw.recipe;

import com.google.gson.JsonObject;
import net.engerbest.mptw.Mptw;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "mptw");

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, "mptw");

    public static final RegistryObject<RecipeSerializer<BlockHittingRecipe>> BLOCK_HITTING_SERIALIZER =
            SERIALIZERS.register("block_hitting", () -> new RecipeSerializer<>() {
                @Override
                public BlockHittingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
                    Ingredient tool = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "tool"));
                    Ingredient block = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "block"));

                    JsonObject resultJson = GsonHelper.getAsJsonObject(json, "result");
                    ItemStack output = ShapedRecipe.itemStackFromJson(resultJson);

                    float chance = GsonHelper.getAsFloat(json, "chance");

                    return new BlockHittingRecipe(recipeId, tool, block, output, chance);
                }

                @Override
                public @Nullable BlockHittingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
                    Ingredient tool = Ingredient.fromNetwork(buffer);
                    Ingredient block = Ingredient.fromNetwork(buffer);
                    ItemStack output = buffer.readItem();
                    float chance = buffer.readFloat();
                    return new BlockHittingRecipe(recipeId, tool, block, output, chance);
                }

                @Override
                public void toNetwork(FriendlyByteBuf buffer, BlockHittingRecipe recipe) {
                    recipe.getTool().toNetwork(buffer);
                    recipe.getBlockIngredient().toNetwork(buffer);
                    buffer.writeItem(recipe.getResultItem(net.minecraft.core.RegistryAccess.EMPTY));
                    buffer.writeFloat(recipe.getChance());
                }
            });

    public static final RegistryObject<RecipeType<BlockHittingRecipe>> BLOCK_HITTING_TYPE =
            TYPES.register("block_hitting", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "block_hitting";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
