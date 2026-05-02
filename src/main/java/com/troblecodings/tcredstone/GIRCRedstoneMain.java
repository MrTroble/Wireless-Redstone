package com.troblecodings.tcredstone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.troblecodings.tcredstone.init.GIRCInit;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(GIRCRedstoneMain.MODID)
public class GIRCRedstoneMain {

    public static final DataComponentType<CompoundTag> COMPOUND_DATA =
            DataComponentType.<CompoundTag>builder().persistent(CompoundTag.CODEC)
                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG).build();

    public GIRCRedstoneMain(final IEventBus modEventBus) {
        GIRCInit.init(modEventBus);
    }

    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "gircredstone";

}
