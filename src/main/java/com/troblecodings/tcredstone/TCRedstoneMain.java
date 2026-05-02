package com.troblecodings.tcredstone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.troblecodings.tcredstone.init.TCInit;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;

public class TCRedstoneMain implements ModInitializer {

    public static final DataComponentType<CompoundTag> COMPOUND_DATA =
            DataComponentType.<CompoundTag>builder().persistent(CompoundTag.CODEC)
                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG).build();

    public static final String MODID = "tcredstone";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        TCInit.init();
        TCInit.registerDataComponents();
    }
}
