package eu.gir.gircredstone.init;

import java.util.function.Supplier;

import eu.gir.gircredstone.GIRCRedstoneMain;
import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.block.BlockRedstoneEmitter;
import eu.gir.gircredstone.item.Linkingtool;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GIRCInit {
	
	public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, GIRCRedstoneMain.MODID);
	public static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, GIRCRedstoneMain.MODID);
	public static final DeferredRegister<BlockEntityType<?>> TILEENTITY_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, GIRCRedstoneMain.MODID);
	
	public static RegistryObject<Block> RS_ACCEPTOR = internalRegisterBlock("acceptor", () -> new BlockRedstoneAcceptor(BlockBehaviour.Properties.of(Material.METAL).strength(1.5f, 6.0f).requiresCorrectToolForDrops()));
	public static RegistryObject<Block> RS_EMITTER = internalRegisterBlock("emitter", () -> new BlockRedstoneEmitter(BlockBehaviour.Properties.of(Material.METAL).strength(1.5f, 6.0f).requiresCorrectToolForDrops()));
	
	public static RegistryObject<Item> RS_LINKER = ITEM_REGISTRY.register("linker", () -> new Linkingtool(new Properties().tab(CreativeModeTab.TAB_REDSTONE)));
	
	public static RegistryObject<BlockEntityType<?>> EMITER_TILE = TILEENTITY_REGISTRY.register("emitter", () -> BlockEntityType.Builder.of(TileRedstoneEmitter::new, RS_EMITTER.get()).build(null));
	
	private static RegistryObject<Block> internalRegisterBlock(final String name, final Supplier<Block> sup) {
		final RegistryObject<Block> registerObject = BLOCK_REGISTRY.register(name, sup);
		ITEM_REGISTRY.register(name, () -> new BlockItem(registerObject.get(), new Properties().tab(CreativeModeTab.TAB_REDSTONE)));
		return registerObject;
	}
	
	public static void init() {
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEM_REGISTRY.register(bus);
		BLOCK_REGISTRY.register(bus);
		TILEENTITY_REGISTRY.register(bus);
	}
	
}
