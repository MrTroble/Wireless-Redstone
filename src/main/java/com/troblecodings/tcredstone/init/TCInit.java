package com.troblecodings.tcredstone.init;

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

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class TCInit {

    public static final Item RS_LINKER = registerItem("linker",
            settings -> new Linkingtool(settings, null, TCInit::acceptAcceptor,
                    TCRedstoneMain.COMPOUND_DATA),
            CreativeModeTabs.REDSTONE_BLOCKS);
    public static final Item RS_MULTILINKER =
            registerItem(
                    "multilinker", settings -> new MultiLinkingTool(settings, null,
                            TCInit::acceptAcceptor, TCRedstoneMain.COMPOUND_DATA),
                    CreativeModeTabs.REDSTONE_BLOCKS);
    public static final Item REMOTE_ACTIVATOR = registerItem("activator",
            settings -> new RemoteActivator(settings, null, TCInit::acceptAcceptor),
            CreativeModeTabs.REDSTONE_BLOCKS);

    public static final Block RS_ACCEPTOR = registerBlock("acceptor",
            settings -> new BlockRedstoneAcceptor(
                    settings.strength(1.5f, 6.0f).requiresCorrectToolForDrops()),
            CreativeModeTabs.REDSTONE_BLOCKS);

    public static final Block RS_EMITTER = registerBlock("emitter",
            settings -> new BlockRedstoneEmitter(
                    settings.strength(1.5f, 6.0f).requiresCorrectToolForDrops()),
            CreativeModeTabs.REDSTONE_BLOCKS);

    public static final Block RS_MULTI_EMITTER = registerBlock("multiemitter",
            settings -> new BlockRedstoneMultiEmitter(
                    settings.strength(1.5f, 6.0f).requiresCorrectToolForDrops()),
            CreativeModeTabs.REDSTONE_BLOCKS);

    public static final BlockEntityType<TileRedstoneEmitter> EMITER_TILE =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(TCRedstoneMain.MODID, "emitter"),
                    FabricBlockEntityTypeBuilder
                            .<TileRedstoneEmitter>create(TileRedstoneEmitter::new, RS_EMITTER)
                            .build());

    public static final BlockEntityType<TileRedstoneMultiEmitter> MULTI_EMITER_TILE =
            Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(TCRedstoneMain.MODID, "multiemitter"),
                    FabricBlockEntityTypeBuilder.<TileRedstoneMultiEmitter>create(
                            TileRedstoneMultiEmitter::new, RS_MULTI_EMITTER).build());

    private TCInit() {
    }

    public static boolean acceptAcceptor(final Level level, final BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof BlockRedstoneAcceptor;
    }

    private static Block registerBlock(final String name,
            final Function<BlockBehaviour.Properties, Block> factory,
            final ResourceKey<CreativeModeTab> group) {
        final Identifier id = Identifier.fromNamespaceAndPath(TCRedstoneMain.MODID, name);
        final ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
        final Block block = factory.apply(BlockBehaviour.Properties.of().setId(blockKey));
        registerBlockItem(name, block, group);
        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static Item registerBlockItem(final String name, final Block block,
            final ResourceKey<CreativeModeTab> group) {
        final ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM,
                Identifier.fromNamespaceAndPath(TCRedstoneMain.MODID, name));
        final Item item = Registry.register(BuiltInRegistries.ITEM, itemKey, new BlockItem(block,
                new Item.Properties().setId(itemKey).useBlockDescriptionPrefix()));
        CreativeModeTabEvents.modifyOutputEvent(group).register(output -> output.accept(item));
        return item;
    }

    private static Item registerItem(final String name,
            final Function<Item.Properties, Item> factory,
            final ResourceKey<CreativeModeTab> group) {
        final ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM,
                Identifier.fromNamespaceAndPath(TCRedstoneMain.MODID, name));
        final Item item = factory.apply(new Item.Properties().setId(itemKey));
        CreativeModeTabEvents.modifyOutputEvent(group).register(output -> output.accept(item));
        return Registry.register(BuiltInRegistries.ITEM, itemKey, item);
    }

    public static void registerDataComponents() {
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE,
                Identifier.fromNamespaceAndPath(TCRedstoneMain.MODID, "compound_data"),
                TCRedstoneMain.COMPOUND_DATA);
    }

    public static void init() {
    }

}
