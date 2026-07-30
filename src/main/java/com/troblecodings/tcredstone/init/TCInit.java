package com.troblecodings.tcredstone.init;

import java.util.Set;
import java.util.function.Function;

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
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

public final class TCInit {

    public static final DeferredRegister.Items ITEM_REGISTRY =
            DeferredRegister.createItems(TCRedstoneMain.MODID);
    public static final DeferredRegister.Blocks BLOCK_REGISTRY =
            DeferredRegister.createBlocks(TCRedstoneMain.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILEENTITY_REGISTRY =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TCRedstoneMain.MODID);

    public static final DeferredBlock<BlockRedstoneAcceptor> RS_ACCEPTOR = internalRegisterBlock(
            "acceptor", BlockRedstoneAcceptor::new,
            BlockBehaviour.Properties.of().strength(1.5f, 6.0f).requiresCorrectToolForDrops());
    public static final DeferredBlock<BlockRedstoneEmitter> RS_EMITTER = internalRegisterBlock(
            "emitter", BlockRedstoneEmitter::new,
            BlockBehaviour.Properties.of().strength(1.5f, 6.0f).requiresCorrectToolForDrops());
    public static final DeferredBlock<BlockRedstoneMultiEmitter> RS_MULTI_EMITTER =
            internalRegisterBlock("multiemitter", BlockRedstoneMultiEmitter::new,
                    BlockBehaviour.Properties.of().strength(1.5f, 6.0f)
                            .requiresCorrectToolForDrops());

    public static boolean acceptAcceptor(final Level level, final BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof BlockRedstoneAcceptor;
    }

    public static final DeferredItem<Linkingtool> RS_LINKER =
            ITEM_REGISTRY.registerItem("linker", props -> new Linkingtool(props, null,
                    TCInit::acceptAcceptor, TCRedstoneMain.COMPOUND_DATA), new Item.Properties());
    public static final DeferredItem<MultiLinkingTool> RS_MULTILINKER =
            ITEM_REGISTRY.registerItem(
                    "multilinker", props -> new MultiLinkingTool(props, null,
                            TCInit::acceptAcceptor, TCRedstoneMain.COMPOUND_DATA),
                    new Item.Properties());
    public static final DeferredItem<RemoteActivator> REMOTE_ACTIVATOR = ITEM_REGISTRY.registerItem(
            "activator", props -> new RemoteActivator(props, null, TCInit::acceptAcceptor),
            new Item.Properties());

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> EMITER_TILE =
            TILEENTITY_REGISTRY.register("emitter",
                    () -> new BlockEntityType<>(TileRedstoneEmitter::new,
                            Set.of(RS_EMITTER.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> MULTI_EMITER_TILE =
            TILEENTITY_REGISTRY.register("multiemitter",
                    () -> new BlockEntityType<>(TileRedstoneMultiEmitter::new,
                            Set.of(RS_MULTI_EMITTER.get())));

    private TCInit() {
    }

    private static <T extends Block> DeferredBlock<T> internalRegisterBlock(final String name,
            final Function<BlockBehaviour.Properties, T> factory,
            final BlockBehaviour.Properties props) {
        final DeferredBlock<T> block = BLOCK_REGISTRY.registerBlock(name, factory, props);
        ITEM_REGISTRY.registerSimpleBlockItem(block);
        return block;
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
