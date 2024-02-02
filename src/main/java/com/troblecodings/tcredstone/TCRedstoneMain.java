package com.troblecodings.tcredstone;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.troblecodings.tcredstone.init.TCInit;

public class TCRedstoneMain implements ModInitializer {
	
	public static final String MODID = "tcredstone";
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize() {
		TCInit.init();
	}
}