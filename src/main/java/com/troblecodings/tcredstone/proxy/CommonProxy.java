package com.troblecodings.tcredstone.proxy;

import com.troblecodings.tcredstone.init.TCRedstoneBlocks;
import com.troblecodings.tcredstone.init.TCRedstoneItems;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    
    public void preinit(final FMLPreInitializationEvent event) {
        TCRedstoneBlocks.init();
        TCRedstoneItems.init();
        
        MinecraftForge.EVENT_BUS.register(TCRedstoneBlocks.class);
        MinecraftForge.EVENT_BUS.register(TCRedstoneItems.class);
    }
    
    public void init(final FMLInitializationEvent event) {

    }

    public void postinit(final FMLPostInitializationEvent event) {

    }

}
