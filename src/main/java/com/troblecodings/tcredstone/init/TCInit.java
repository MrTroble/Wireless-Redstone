package com.troblecodings.tcredstone.init;

import java.util.function.Supplier;

import com.troblecodings.linkableapi.Linkingtool;
import com.troblecodings.linkableapi.MultiLinkingTool;
import com.troblecodings.tcredstone.TCRedstoneMain;
import com.troblecodings.tcredstone.block.BlockRedstoneAcceptor;
import com.troblecodings.tcredstone.block.BlockRedstoneEmitter;
import com.troblecodings.tcredstone.block.BlockRedstoneMultiEmitter;
import com.troblecodings.tcredstone.item.RemoteActivator;
import com.troblecodings.tcredstone.tile.TileRedstoneEmitter;
import com.troblecodings.tcredstone.tile.TileRedstoneMultiEmitter;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

public class TCInit {

    public static final DeferredRegister<Item> ITEM_REGISTRY =
            DeferredRegister.create(Registries.ITEM, TCRedstoneMain.MODID);
    public static final DeferredRegister<Block> BLOCK_REGISTRY =
            DeferredRegister.create(Registries.BLOCK, TCRedstoneMain.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILEENTITY_REGISTRY =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TCRedstoneMain.MODID);

    public static final DeferredHolder<Block, Block> RS_ACCEPTOR = internalRegisterBlock("acceptor",
            () -> new BlockRedstoneAcceptor(BlockBehaviour.Properties.of() // TODO Material.METAL
                    .strength(1.5f, 6.0f).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> RS_EMITTER = internalRegisterBlock("emitter",
            () -> new BlockRedstoneEmitter(BlockBehaviour.Properties.of() // TODO Material.METAL
                    .strength(1.5f, 6.0f).requiresCorrectToolForDrops()));
    public static final DeferredHolder<Block, Block> RS_MULTI_EMITTER = internalRegisterBlock(
            "multiemitter", () -> new BlockRedstoneMultiEmitter(BlockBehaviour.Properties.of()
                    .strength(1.5f, 6.0f).requiresCorrectToolForDrops())); // TODO Material.METAL

    public static boolean acceptAcceptor(final Level level, final BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof BlockRedstoneAcceptor;
    }

    public static final DeferredHolder<Item, Item> RS_LINKER = ITEM_REGISTRY.register("linker",
            () -> new Linkingtool(null, TCInit::acceptAcceptor, TCRedstoneMain.COMPOUND_DATA));
    public static final DeferredHolder<Item, Item> RS_MULTILINKER = ITEM_REGISTRY.register(
            "multilinker",
            () -> new MultiLinkingTool(null, TCInit::acceptAcceptor, TCRedstoneMain.COMPOUND_DATA));
    public static final DeferredHolder<Item, Item> REMOTE_ACTIVATOR = ITEM_REGISTRY
            .register("activator", () -> new RemoteActivator(null, TCInit::acceptAcceptor));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> EMITER_TILE =
            TILEENTITY_REGISTRY.register("emitter", () -> BlockEntityType.Builder
                    .of(TileRedstoneEmitter::new, RS_EMITTER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> MULTI_EMITER_TILE =
            TILEENTITY_REGISTRY.register("multiemitter", () -> BlockEntityType.Builder
                    .of(TileRedstoneMultiEmitter::new, RS_MULTI_EMITTER.get()).build(null));

    private static final DeferredHolder<Block, Block> internalRegisterBlock(final String name,
            final Supplier<Block> sup) {
        final DeferredHolder<Block, Block> registerObject = BLOCK_REGISTRY.register(name, sup);
        ITEM_REGISTRY.register(name, () -> new BlockItem(registerObject.get(), new Properties()));
        return registerObject;
    }

    public static void init(final IEventBus bus) {
        bus.register(TCInit.class);
        ITEM_REGISTRY.register(bus);
        BLOCK_REGISTRY.register(bus);
        TILEENTITY_REGISTRY.register(bus);
    }

    @SubscribeEvent
    public static void onCreativeTabs(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.REDSTONE_BLOCKS)) {
            ITEM_REGISTRY.getEntries().forEach(holder -> event.accept(holder.get()));
        }
    }

    @SubscribeEvent
    public static void registerDataComponents(final RegisterEvent event) {
        event.register(BuiltInRegistries.DATA_COMPONENT_TYPE.key(), registry -> {
            registry.register(
                    ResourceLocation.fromNamespaceAndPath(TCRedstoneMain.MODID, "compound_data"),
                    TCRedstoneMain.COMPOUND_DATA);
        });
    }

}
