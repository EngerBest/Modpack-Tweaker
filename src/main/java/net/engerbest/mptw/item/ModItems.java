package net.engerbest.mptw.item;

import net.engerbest.mptw.Mptw;
import net.engerbest.mptw.item.custom.FireSticksItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Mptw.MOD_ID);

    public static final RegistryObject<FireSticksItem> FIRE_STICKS = ITEMS.register("fire_sticks",
            () -> new FireSticksItem(new Item.Properties().durability(8)));

    public static final RegistryObject<Item> FLINT_FRAGMENT = ITEMS.register("flint_fragment",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
