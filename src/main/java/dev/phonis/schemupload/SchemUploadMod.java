package dev.phonis.schemupload;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = SchemUploadMod.MODID, version = SchemUploadMod.VERSION)
public class SchemUploadMod {

    public static final String MODID = "schemuploadmod";
    public static final String VERSION = "1.0";
    public static final int protocolVersion = 1;
    public static final String channelName = "schemupload:main";

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

}
