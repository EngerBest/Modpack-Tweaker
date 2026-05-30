package net.engerbest.mptw.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockHittingRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final Ingredient tool;
    private final Ingredient blockIngredient;
    private final ItemStack output;
    private final float chance;

    public BlockHittingRecipe(ResourceLocation id, Ingredient tool, Ingredient blockIngredient, ItemStack output, float chance) {
        this.id = id;
        this.tool = tool;
        this.blockIngredient = blockIngredient;
        this.output = output;
        this.chance = chance;
    }

    public boolean matches(ItemStack heldItem, BlockState state) {
        return this.tool.test(heldItem) && this.blockIngredient.test(new ItemStack(state.getBlock().asItem()));
    }

    public Ingredient getTool() {
        return tool;
    }
    public Ingredient getBlockIngredient() {
        return blockIngredient;
    }
    public float getChance() {
        return chance;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BLOCK_HITTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.BLOCK_HITTING_TYPE.get();
    }
}
