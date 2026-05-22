package net.engerbest.mptw.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.DoubleValue IGNITE_CHANCE;

    static {
        BUILDER.push("Modpack Tweaker Config");

        IGNITE_CHANCE = BUILDER
                .comment("Chance of successful igniting the block (0.0 = 0%, 1.0 = 100%)")
                .defineInRange("igniteChance", 0.5, 0.0, 1.0);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
