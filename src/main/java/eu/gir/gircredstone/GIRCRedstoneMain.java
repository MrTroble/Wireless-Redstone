package eu.gir.gircredstone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.gir.gircredstone.init.GIRCInit;
import net.minecraftforge.fml.common.Mod;

@Mod(GIRCRedstoneMain.MODID)
public class GIRCRedstoneMain {

    public GIRCRedstoneMain() {
        GIRCInit.init();
    }

    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "gircredstone";

}
