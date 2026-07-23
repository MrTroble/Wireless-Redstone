package com.troblecodings.tcredstone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.troblecodings.tcredstone.init.TCInit;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(TCRedstoneMain.MODID)
public class TCRedstoneMain {

    public static final DataComponentType<CompoundTag> COMPOUND_DATA =
            DataComponentType.<CompoundTag>builder().persistent(CompoundTag.CODEC)
                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG).build();

    public TCRedstoneMain(final IEventBus modEventBus) {
        TCInit.init(modEventBus);
    }

    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "gircredstone";

}
