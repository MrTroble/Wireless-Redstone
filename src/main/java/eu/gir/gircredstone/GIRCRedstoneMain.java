package eu.gir.gircredstone;

import org.apache.logging.log4j.Logger;

import eu.gir.gircredstone.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GIRCRedstoneMain.MODID)
public class GIRCRedstoneMain
{
    public static final String MODID = "gircredstone";

    public static Logger logger;

	@SidedProxy(serverSide = "eu.gir.gircredstone.proxy.CommonProxy", clientSide = "eu.gir.gircredstone.proxy.ClientProxy")
	public static CommonProxy PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        PROXY.preinit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
}
