package keybindings;

import dev.phonis.schemupload.schematica.SchemUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyEvent {

    @SubscribeEvent
    public void onKey(KeyInputEvent event) {
        if (Keybinds.uploadKeybinding.isPressed()) {
            SchemUtils.uploadSchem();
        }
    }

}
