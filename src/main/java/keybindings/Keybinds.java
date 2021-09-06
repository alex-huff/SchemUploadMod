package keybindings;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds {

    public static final KeyBinding uploadKeybinding = new KeyBinding("Upload Schematic", Keyboard.KEY_U, "Schem Upload");

    public static void register() {
        ClientRegistry.registerKeyBinding(uploadKeybinding);
    }

}
