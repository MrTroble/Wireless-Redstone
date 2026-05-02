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

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TCInit {

    public static final Item RS_LINKER = registerItem("linker",
            settings -> new Linkingtool(settings, null, TCInit::acceptAcceptor),
            ItemGroups.REDSTONE);
    public static final Item RS_MULTILINKER = registerItem("multilinker",
            settings -> new MultiLinkingTool(settings, null, TCInit::acceptAcceptor),
            ItemGroups.REDSTONE);
    public static final Item REMOTE_ACTIVATOR = registerItem("activator",
            settings -> new RemoteActivator(settings, TCInit::acceptAcceptor), ItemGroups.REDSTONE);

    public static final Block RS_ACCEPTOR = registerBlock("acceptor",
            settings -> new BlockRedstoneAcceptor(settings.strength(1.5f, 6.0f)),
            ItemGroups.REDSTONE);

    public static final Block RS_EMITTER = registerBlock("emitter",
            settings -> new BlockRedstoneEmitter(settings.strength(1.5f, 6.0f)),
            ItemGroups.REDSTONE);

    public static final Block RS_MULTI_EMITTER = registerBlock("multiemitter",
            settings -> new BlockRedstoneMultiEmitter(settings.strength(1.5f, 6.0f)),
            ItemGroups.REDSTONE);

    public static final BlockEntityType<TileRedstoneEmitter> EMITER_TILE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, Identifier.of(TCRedstoneMain.MODID, "emitter"),
            FabricBlockEntityTypeBuilder
                    .<TileRedstoneEmitter>create(TileRedstoneEmitter::new, RS_EMITTER)
                    .build());

    public static final BlockEntityType<TileRedstoneMultiEmitter> MULTI_EMITER_TILE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(TCRedstoneMain.MODID, "multiemitter"),
                    FabricBlockEntityTypeBuilder.<TileRedstoneMultiEmitter>create(
                            TileRedstoneMultiEmitter::new, RS_MULTI_EMITTER).build());

    public static boolean acceptAcceptor(final World level, final BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof BlockRedstoneAcceptor;
    }

    private static Block registerBlock(final String name,
            final Function<AbstractBlock.Settings, Block> factory,
            final RegistryKey<ItemGroup> group) {
        final Identifier id = Identifier.of(TCRedstoneMain.MODID, name);
        final RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, id);
        final Block block = factory.apply(AbstractBlock.Settings.create().registryKey(blockKey));
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static Item registerBlockItem(final String name, final Block block,
            final RegistryKey<ItemGroup> group) {
        final RegistryKey<Item> itemKey =
                RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TCRedstoneMain.MODID, name));
        final Item item =
                Registry.register(Registries.ITEM, itemKey, new BlockItem(block, new Settings()
                        .registryKey(itemKey).useBlockPrefixedTranslationKey()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }

    private static Item registerItem(final String name, final Function<Settings, Item> factory,
            final RegistryKey<ItemGroup> group) {
        final RegistryKey<Item> itemKey =
                RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TCRedstoneMain.MODID, name));
        final Item item = factory.apply(new Settings().registryKey(itemKey));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, itemKey, item);
    }

    public static void registerDataComponents() {
        Registry.register(Registries.DATA_COMPONENT_TYPE,
                Identifier.of(TCRedstoneMain.MODID, "compound_data"), TCRedstoneMain.COMPOUND_DATA);
    }

    public static void init() {
    }

}
