package com.troblecodings.tcredstone.init;

import com.troblecodings.linkableapi.Linkingtool;
import com.troblecodings.tcredstone.TCRedstoneMain;
import com.troblecodings.tcredstone.block.BlockRedstoneAcceptor;
import com.troblecodings.tcredstone.block.BlockRedstoneEmitter;
import com.troblecodings.tcredstone.block.BlockRedstoneMultiEmitter;
import com.troblecodings.tcredstone.item.RemoteActivator;
import com.troblecodings.tcredstone.tile.TileRedstoneEmitter;
import com.troblecodings.tcredstone.tile.TileRedstoneMultiEmitter;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Bus.MOD, modid = TCRedstoneMain.MODID)
public final class TCRedstoneInit {

    public static final Block RS_ACCEPTOR = new BlockRedstoneAcceptor(
            net.minecraft.block.Block.Properties.create(Material.IRON).hardnessAndResistance(1.5f,
                    6.0f)).setRegistryName(TCRedstoneMain.MODID, "acceptor");
    public static final Block RS_EMITTER = new BlockRedstoneEmitter(
            net.minecraft.block.Block.Properties.create(Material.IRON).hardnessAndResistance(1.5f,
                    6.0f)).setRegistryName(TCRedstoneMain.MODID, "emitter");
    public static final Block RS_MULTI_EMITTER = new BlockRedstoneMultiEmitter(
            net.minecraft.block.Block.Properties.create(Material.IRON).hardnessAndResistance(1.5f,
                    6.0f)).setRegistryName(TCRedstoneMain.MODID, "multiemitter");

    public static final Item RS_LINKER = new Linkingtool(ItemGroup.REDSTONE,
            TCRedstoneInit::acceptAcceptor).setRegistryName(TCRedstoneMain.MODID, "linker");
    public static final Item REMOTE_ACTIVATOR = new RemoteActivator(ItemGroup.REDSTONE,
            TCRedstoneInit::acceptAcceptor).setRegistryName(TCRedstoneMain.MODID, "activator");

    @SubscribeEvent
    public static void registerBlock(final RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = event.getRegistry();
        registry.register(RS_ACCEPTOR);
        registry.register(RS_EMITTER);
        registry.register(RS_MULTI_EMITTER);
    }

    @SubscribeEvent
    public static void registerItem(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(new ItemBlock(RS_ACCEPTOR, new Properties().group(ItemGroup.REDSTONE))
                .setRegistryName(RS_ACCEPTOR.getRegistryName()));
        registry.register(new ItemBlock(RS_EMITTER, new Properties().group(ItemGroup.REDSTONE))
                .setRegistryName(RS_EMITTER.getRegistryName()));
        registry.register(
                new ItemBlock(RS_MULTI_EMITTER, new Properties().group(ItemGroup.REDSTONE))
                        .setRegistryName(RS_MULTI_EMITTER.getRegistryName()));
        registry.register(RS_LINKER);
        registry.register(REMOTE_ACTIVATOR);
    }

    public static boolean acceptAcceptor(final World level, final BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof BlockRedstoneAcceptor;
    }

    public static final TileEntityType<?> EMITER_TILE = TileEntityType.Builder
            .create(TileRedstoneEmitter::new).build(null)
            .setRegistryName(TCRedstoneMain.MODID, "emitter");
    public static final TileEntityType<?> MULTI_EMITER_TILE = TileEntityType.Builder
            .create(TileRedstoneMultiEmitter::new).build(null)
            .setRegistryName(TCRedstoneMain.MODID, "multiemitter");

    @SubscribeEvent
    public static void registerTE(final RegistryEvent.Register<TileEntityType<?>> evt) {
        evt.getRegistry().register(EMITER_TILE);
        evt.getRegistry().register(MULTI_EMITER_TILE);
    }

}
