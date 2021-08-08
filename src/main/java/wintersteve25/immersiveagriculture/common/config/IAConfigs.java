package wintersteve25.immersiveagriculture.common.config;

import fictioncraft.wintersteve25.fclib.common.helper.MiscHelper;
import net.minecraftforge.common.ForgeConfigSpec;

public class IAConfigs {
    public static final String CAT_GENERAL = "general";

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.IntValue CHANCE_WITHOUT_SCYTHE;
    public static ForgeConfigSpec.IntValue CHANCE_CONSUME_WATER;
    public static ForgeConfigSpec.IntValue SPRINKLER_CONSUME_WATER_AMOUNT;
    public static ForgeConfigSpec.IntValue SPRINKLER_OPERATION_TIME;
    public static ForgeConfigSpec.IntValue SPRINKLER_OPERATION_POWER;
    public static ForgeConfigSpec.IntValue SPRINKLER_OPERATION_LENGTH;
    public static ForgeConfigSpec.IntValue SPRINKLER_RANGE;
    public static ForgeConfigSpec.BooleanValue PRINT_EXAMPLE;

    static {
        ForgeConfigSpec.Builder SERVERBUILDER = new ForgeConfigSpec.Builder();

        SERVERBUILDER.comment("General").push(CAT_GENERAL);
        CHANCE_WITHOUT_SCYTHE = SERVERBUILDER.comment("Chance to obtain the drop from the crop without using a scythe").defineInRange("chanceWithoutScythe", 35, 0, MiscHelper.INT_MAX);
        CHANCE_CONSUME_WATER = SERVERBUILDER.comment("Chance for water to be consumed by moisturing farmlands").defineInRange("chanceConsumeWater", 60, 0, MiscHelper.INT_MAX);
        SPRINKLER_CONSUME_WATER_AMOUNT = SERVERBUILDER.comment("The amount of water (mb) that is consumed every sprinkler operation").defineInRange("sprinklerWaterConsumption", 100, 0, MiscHelper.INT_MAX);
        SPRINKLER_OPERATION_TIME = SERVERBUILDER.comment("Ticks between each sprinkler operation").defineInRange("sprinklerTickOperation", 100, 1, MiscHelper.INT_MAX);
        SPRINKLER_OPERATION_POWER = SERVERBUILDER.comment("Power taken each tick during each sprinkler operation").defineInRange("sprinklerPowerOperation", 32, 0, MiscHelper.INT_MAX);
        SPRINKLER_OPERATION_LENGTH = SERVERBUILDER.comment("How long should each operation last?").defineInRange("sprinklerOperationLength", 120, 1, MiscHelper.INT_MAX);
        SPRINKLER_RANGE = SERVERBUILDER.comment("Range of operation of the sprinkler").defineInRange("sprinklerRange", 9, 1, MiscHelper.INT_MAX);
        PRINT_EXAMPLE = SERVERBUILDER.comment("Should the example json config (for configuring custom crop growth, more info on wiki) be printed on load?").define("printExample", true);
        SERVERBUILDER.pop();

        SERVER_CONFIG = SERVERBUILDER.build();
    }
}
