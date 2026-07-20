package com.troblecodings.tcredstone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.troblecodings.tcredstone.init.TCInit;

import net.fabricmc.api.ModInitializer;
import net.minecraft.component.ComponentType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.codec.PacketCodecs;

public class TCRedstoneMain implements ModInitializer {

    public static final ComponentType<NbtCompound> COMPOUND_DATA =
            ComponentType.<NbtCompound>builder().codec(NbtCompound.CODEC)
                    .packetCodec(PacketCodecs.NBT_COMPOUND).build();

    public static final String MODID = "tcredstone";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        TCInit.init();
        TCInit.registerDataComponents();
    }
}
