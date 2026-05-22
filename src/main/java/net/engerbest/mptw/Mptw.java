package net.engerbest.mptw;

import com.mojang.logging.LogUtils;
import net.engerbest.mptw.config.ModCommonConfig;
import net.engerbest.mptw.creativemodetab.ModCreativeModeTabs;
import net.engerbest.mptw.item.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Mptw.MOD_ID)
public class Mptw {
    public static final String MOD_ID = "mptw";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Mptw(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        context.registerConfig(ModConfig.Type.COMMON, ModCommonConfig.SPEC);
    }
}
