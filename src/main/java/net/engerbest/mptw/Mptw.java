package net.engerbest.mptw;

import com.mojang.logging.LogUtils;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.engerbest.mptw.config.ModClothCommonConfig;
import net.engerbest.mptw.creativemodetab.ModCreativeModeTabs;
import net.engerbest.mptw.item.ModItems;
import net.engerbest.mptw.recipe.ModRecipes;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@SuppressWarnings("removal")
@Mod(Mptw.MOD_ID)
public class Mptw {
    public static ModClothCommonConfig CONFIG;
    public static final String MOD_ID = "mptw";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Mptw(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        ModRecipes.register(modEventBus);

        AutoConfig.register(ModClothCommonConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ModClothCommonConfig.class).getConfig();

        modEventBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            net.minecraftforge.fml.ModLoadingContext.get().registerExtensionPoint(
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory(
                            ((minecraft, parentScreen) -> {
                                Screen configScreen = AutoConfig.getConfigScreen(ModClothCommonConfig.class, parentScreen).get();

                                if (configScreen instanceof me.shedaniel.clothconfig2.gui.ClothConfigScreen clothScreen)
                                    clothScreen.setTransparentBackground(true);

                                return configScreen;
                            })
                    )
            );
        });
    }
}
