package com.troblecodings.tcredstone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.troblecodings.tcredstone.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TCRedstoneMain.MODID)
public class TCRedstoneMain {

    public TCRedstoneMain() {
    }

    public static Logger logger = LogManager.getLogger();

    public static final String MODID = "gircredstone";

    @SidedProxy(serverSide = "com.troblecodings.tcredstone.proxy.CommonProxy",
            clientSide = "com.troblecodings.tcredstone.proxy.ClientProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preinit(event);
    }

}
