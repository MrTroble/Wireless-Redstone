package com.troblecodings.tcredstone.proxy;

import com.troblecodings.tcredstone.init.TCRedstoneBlocks;
import com.troblecodings.tcredstone.init.TCRedstoneItems;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preinit(final FMLPreInitializationEvent event) {
        super.preinit(event);
        MinecraftForge.EVENT_BUS.register(ClientProxy.class);
    }
    
    @Override
    public void init(final FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postinit(final FMLPostInitializationEvent event) {
        super.postinit(event);
    }
    
    @SubscribeEvent
    public static void register(final ModelRegistryEvent event) {
        for (int i = 0; i < TCRedstoneBlocks.blocksToRegister.size(); i++) {
            registerModel(Item.getItemFromBlock(TCRedstoneBlocks.blocksToRegister.get(i)));
        }

        for (int j = 0; j < TCRedstoneItems.itemsToRegister.size(); j++) {
            registerModel(TCRedstoneItems.itemsToRegister.get(j));
        }
    }

    private static void registerModel(final Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}
