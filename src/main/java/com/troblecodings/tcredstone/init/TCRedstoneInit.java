package com.troblecodings.tcredstone.init;

import java.util.function.Supplier;

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
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class TCRedstoneInit {

    private TCRedstoneInit() {
    }

    public static final DeferredRegister<Item> ITEM_REGISTRY = new DeferredRegister<Item>(
            ForgeRegistries.ITEMS, TCRedstoneMain.MODID);
    public static final DeferredRegister<Block> BLOCK_REGISTRY = new DeferredRegister<Block>(
            ForgeRegistries.BLOCKS, TCRedstoneMain.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILEENTITY_REGISTRY =
            new DeferredRegister<TileEntityType<?>>(ForgeRegistries.TILE_ENTITIES,
                    TCRedstoneMain.MODID);

    public static final RegistryObject<Block> RS_ACCEPTOR = internalRegisterBlock("acceptor",
            () -> new BlockRedstoneAcceptor(
                    Block.Properties.of(Material.METAL).strength(1.5f, 6.0f)));
    public static final RegistryObject<Block> RS_EMITTER = internalRegisterBlock("emitter",
            () -> new BlockRedstoneEmitter(
                    Block.Properties.of(Material.METAL).strength(1.5f, 6.0f)));
    public static final RegistryObject<Block> RS_MULTI_EMITTER = internalRegisterBlock(
            "multiemitter", () -> new BlockRedstoneMultiEmitter(
                    Block.Properties.of(Material.METAL).strength(1.5f, 6.0f)));

    public static boolean acceptAcceptor(final World level, final BlockPos pos) {
        return level.getBlockState(pos).getBlock() instanceof BlockRedstoneAcceptor;
    }

    public static final RegistryObject<Item> RS_LINKER = ITEM_REGISTRY.register("linker",
            () -> new Linkingtool(ItemGroup.TAB_REDSTONE, TCRedstoneInit::acceptAcceptor));
    public static final RegistryObject<Item> REMOTE_ACTIVATOR = ITEM_REGISTRY.register("activator",
            () -> new RemoteActivator(ItemGroup.TAB_REDSTONE, TCRedstoneInit::acceptAcceptor));

    public static final RegistryObject<TileEntityType<?>> EMITER_TILE = TILEENTITY_REGISTRY
            .register("emitter", () -> TileEntityType.Builder
                    .of(TileRedstoneEmitter::new, RS_EMITTER.get()).build(null));

    public static final RegistryObject<TileEntityType<?>> MULTI_EMITER_TILE = TILEENTITY_REGISTRY
            .register("multiemitter", () -> TileEntityType.Builder
                    .of(TileRedstoneMultiEmitter::new, RS_MULTI_EMITTER.get()).build(null));

    private static final RegistryObject<Block> internalRegisterBlock(final String name,
            final Supplier<Block> sup) {
        final RegistryObject<Block> registerObject = BLOCK_REGISTRY.register(name, sup);
        ITEM_REGISTRY.register(name, () -> new BlockItem(registerObject.get(),
                new Properties().tab(ItemGroup.TAB_REDSTONE)));
        return registerObject;
    }

    public static void init() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(TCRedstoneInit.class);
        ITEM_REGISTRY.register(bus);
        BLOCK_REGISTRY.register(bus);
        TILEENTITY_REGISTRY.register(bus);
    }
}
