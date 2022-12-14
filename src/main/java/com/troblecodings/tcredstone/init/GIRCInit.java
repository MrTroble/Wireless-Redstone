package com.troblecodings.tcredstone.init;

import java.util.function.Supplier;

import eu.gir.gircredstone.GIRCRedstoneMain;
import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.block.BlockRedstoneEmitter;
import eu.gir.gircredstone.block.BlockRedstoneMultiEmitter;
import eu.gir.gircredstone.item.RemoteActivator;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import eu.gir.gircredstone.tile.TileRedstoneMultiEmitter;
import eu.gir.linkableapi.Linkingtool;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GIRCInit {

    public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister
            .create(ForgeRegistries.ITEMS, GIRCRedstoneMain.MODID);
    public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister
            .create(ForgeRegistries.BLOCKS, GIRCRedstoneMain.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILEENTITY_REGISTRY = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITY_TYPES, GIRCRedstoneMain.MODID);

    public static final RegistryObject<Block> RS_ACCEPTOR = internalRegisterBlock("acceptor",
            () -> new BlockRedstoneAcceptor(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(1.5f, 6.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> RS_EMITTER = internalRegisterBlock("emitter",
            () -> new BlockRedstoneEmitter(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(1.5f, 6.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> RS_MULTI_EMITTER = internalRegisterBlock(
            "multiemitter", () -> new BlockRedstoneMultiEmitter(BlockBehaviour.Properties
                    .of(Material.METAL).strength(1.5f, 6.0f).requiresCorrectToolForDrops()));

    public static boolean acceptAcceptor(final Level level, final BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof BlockRedstoneAcceptor;
    }

    public static final RegistryObject<Item> RS_LINKER = ITEM_REGISTRY.register("linker",
            () -> new Linkingtool(null, GIRCInit::acceptAcceptor));
    public static final RegistryObject<Item> REMOTE_ACTIVATOR = ITEM_REGISTRY.register("activator",
            () -> new RemoteActivator());

    public static final RegistryObject<BlockEntityType<?>> EMITER_TILE = TILEENTITY_REGISTRY
            .register("emitter", () -> BlockEntityType.Builder
                    .of(TileRedstoneEmitter::new, RS_EMITTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<?>> MULTI_EMITER_TILE = TILEENTITY_REGISTRY
            .register("multiemitter", () -> BlockEntityType.Builder
                    .of(TileRedstoneMultiEmitter::new, RS_MULTI_EMITTER.get()).build(null));

    private static final RegistryObject<Block> internalRegisterBlock(final String name,
            final Supplier<Block> sup) {
        final RegistryObject<Block> registerObject = BLOCK_REGISTRY.register(name, sup);
        ITEM_REGISTRY.register(name, () -> new BlockItem(registerObject.get(), new Properties()));
        return registerObject;
    }

    public static void init() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(GIRCInit.class);
        ITEM_REGISTRY.register(bus);
        BLOCK_REGISTRY.register(bus);
        TILEENTITY_REGISTRY.register(bus);
    }

    @SubscribeEvent
    public static void onCreativeTabs(final CreativeModeTabEvent.BuildContents event) {
        event.registerSimple(CreativeModeTabs.f_257028_,
                ITEM_REGISTRY.getEntries().stream().map(reg -> reg.get()).toArray(Item[]::new));
    }

}
