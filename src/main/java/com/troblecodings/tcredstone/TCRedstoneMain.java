package com.troblecodings.tcredstone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.troblecodings.tcredstone.init.TCRedstoneInit;

import net.minecraftforge.fml.common.Mod;

@Mod(TCRedstoneMain.MODID)
public class TCRedstoneMain {

    public TCRedstoneMain() {
        TCRedstoneInit.init();
    }

    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "gircredstone";
    
    public void test() {
        //literally do nothing
    }

}
