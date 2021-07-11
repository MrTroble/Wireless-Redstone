package eu.gir.gircredstone;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GIRCRedstoneMain.MODID, name = GIRCRedstoneMain.NAME, version = GIRCRedstoneMain.VERSION)
public class GIRCRedstoneMain
{
    public static final String MODID = "gircredstone";
    public static final String NAME = "GIRC Redstone";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
}
