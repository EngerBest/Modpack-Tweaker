package net.engerbest.mptw.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.network.chat.Component;

@Config(name = "mptw-common")
public class ModClothCommonConfig implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    @ConfigEntry.Gui.Tooltip
    public int igniteChance = 50;
}
