package com.troblecodings.tcredstone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.troblecodings.tcredstone.init.TCInit;

import net.minecraftforge.fml.common.Mod;

@Mod(TCRedstoneMain.MODID)
public class TCRedstoneMain {

    public TCRedstoneMain() {
        TCInit.init();
    }

    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "gircredstone";

}
