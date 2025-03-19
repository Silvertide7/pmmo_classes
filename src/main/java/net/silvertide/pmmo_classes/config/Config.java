package net.silvertide.pmmo_classes.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec SERVER_CONFIG;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        setupServer(SERVER_BUILDER);

        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    private static void setupServer(ForgeConfigSpec.Builder builder) {
        buildBasics(builder);
    }

    public static ForgeConfigSpec.ConfigValue<Integer> NUM_CLASSES_ALLOWED;
    public static ForgeConfigSpec.ConfigValue<Integer> MAX_LEVEL_ALLOWED;
    public static ForgeConfigSpec.ConfigValue<Integer> SECOND_CLASS_LEVEL_REQ;

    public static ForgeConfigSpec.ConfigValue<Integer> SUB_CLASS_LEVEL_REQ;

    public static ForgeConfigSpec.ConfigValue<Boolean> ASCENDED_CLASSES_ACTIVE;
    public static ForgeConfigSpec.ConfigValue<Integer> ASCENDED_CLASS_LEVEL_REQ;

    private static void buildBasics(ForgeConfigSpec.Builder builder) {
        builder.push("Primary Classes");

        builder.comment("The number of classes a player can have at one time. Allowed values: 1 or 2.");
        NUM_CLASSES_ALLOWED = builder.worldRestart().defineInRange("numClassesAllowed", 2, 1, 2);

        builder.comment("The highest level a class can reach.");
        MAX_LEVEL_ALLOWED = builder.worldRestart().defineInRange("maxLevelAllowed", 4, 1, Integer.MAX_VALUE);

        builder.comment("The level you must reach in your first primary class before you can take a second primary class.");
        builder.comment("If this is set to three then once you reach level three in your first primary class you can learn a second class.");
        builder.comment("This is only active if the number of classes allowed is two.");
        SECOND_CLASS_LEVEL_REQ = builder.worldRestart().defineInRange("secondClassLevelReq", 3, 1, 4);
        builder.pop();

        builder.push("Sub Classes");

        builder.comment("The level you must reach in a primary class before you can take a subclass for that class.");
        builder.comment("If this is set to two then once you reach level two in a primary class you can learn a subclass.");

        SUB_CLASS_LEVEL_REQ = builder.worldRestart().defineInRange("subClassLevelReq", 2, 1, 4);

        builder.pop();

        builder.push("Ascended Classes");
        builder.comment("Ascended classes are classes that are granted to the player based on the two other classes they obtain.");
        builder.comment("Those two classes determine which unique ascended class you get. ");
        builder.comment("If ascended classes should be granted to the player.");
        ASCENDED_CLASSES_ACTIVE = builder.worldRestart().define("ascendedClassesActive", true);

        builder.comment("The level the two primary classes must be before the ascended class is granted.");
        builder.comment("If this is set to 4 then when both of your primary classes reach level 4 you will be granted the ascended class.");
        ASCENDED_CLASS_LEVEL_REQ = builder.worldRestart().defineInRange("ascendedClassLevelReq", 4, 1, 4);

        builder.pop();

    }
}
