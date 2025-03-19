package net.silvertide.pmmo_classes.client.keybindings;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.silvertide.pmmo_classes.PMMOClasses;

public class Keybindings {
    public static final Keybindings INSTANCE = new Keybindings();
    private Keybindings() {}

    private static final String CATEGORY = "key.categories." + PMMOClasses.MOD_ID;

    public final KeyMapping useOpenManageClassesKey = new KeyMapping(
            "key." + PMMOClasses.MOD_ID + ".useOpenManageClassesKey",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_O, -1),
            CATEGORY
    );

}
