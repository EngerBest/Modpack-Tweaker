package net.engerbest.mptw.creativemodetab;

import net.engerbest.mptw.Mptw;
import net.engerbest.mptw.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Mptw.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MPTW_TAB = CREATIVE_MODE_TABS.register("mptw_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.FIRE_STICKS.get()))
                    .title(Component.translatable("creativetab.mptw_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.FIRE_STICKS.get());

                        pOutput.accept(ModItems.FLINT_FRAGMENT.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
