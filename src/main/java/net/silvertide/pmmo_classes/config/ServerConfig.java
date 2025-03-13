package net.silvertide.pmmo_classes.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.ConfigValue<Integer> NUM_CLASSES_ALLOWED;
    public static final ModConfigSpec.ConfigValue<Integer> MAX_LEVEL_ALLOWED;
    public static final ModConfigSpec.ConfigValue<Integer> SECOND_CLASS_LEVEL_REQ;

    public static final ModConfigSpec.ConfigValue<Integer> SUB_CLASS_LEVEL_REQ;

    public static final ModConfigSpec.ConfigValue<Boolean> ASCENDED_CLASSES_ACTIVE;
    public static final ModConfigSpec.ConfigValue<Integer> ASCENDED_CLASS_LEVEL_REQ;

    static {
        BUILDER.push("Primary Classes");

        BUILDER.comment("The number of classes a player can have at one time. Allowed values: 1 or 2.");
        NUM_CLASSES_ALLOWED = BUILDER.worldRestart().defineInRange("numClassesAllowed", 2, 1, 2);

        BUILDER.comment("The highest level a class can reach.");
        MAX_LEVEL_ALLOWED = BUILDER.worldRestart().defineInRange("maxLevelAllowed", 4, 1, 4);

        BUILDER.comment("The level you must reach in your first primary class before you can take a second primary class.");
        BUILDER.comment("If this is set to three then once you reach level three in your first primary class you can learn a second class.");
        BUILDER.comment("This is only active if the number of classes allowed is two.");
        SECOND_CLASS_LEVEL_REQ = BUILDER.worldRestart().defineInRange("secondClassLevelReq", 3, 1, 4);
        BUILDER.pop();

        BUILDER.push("Sub Classes");

        BUILDER.comment("The level you must reach in a primary class before you can take a subclass for that class.");
        BUILDER.comment("If this is set to two then once you reach level two in a primary class you can learn a subclass.");

        SUB_CLASS_LEVEL_REQ = BUILDER.worldRestart().defineInRange("subClassLevelReq", 2, 1, 4);

        BUILDER.pop();


        BUILDER.push("Ascended Classes");
        BUILDER.comment("Ascended classes are classes that are granted to the player based on the two other classes they obtain.");
        BUILDER.comment("Those two classes determine which unique ascended class you get. ");
        BUILDER.comment("If ascended classes should be granted to the player.");
        ASCENDED_CLASSES_ACTIVE = BUILDER.worldRestart().define("ascendedClassesActive", true);

        BUILDER.comment("The level the two primary classes must be before the ascended class is granted.");
        BUILDER.comment("If this is set to 4 then when both of your primary classes reach level 4 you will be granted the ascended class.");
        ASCENDED_CLASS_LEVEL_REQ = BUILDER.worldRestart().defineInRange("ascendedClassLevelReq", 4, 1, 4);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
