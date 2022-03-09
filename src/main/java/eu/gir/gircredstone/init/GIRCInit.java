package eu.gir.gircredstone.init;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Bus.MOD, modid = GIRCRedstoneMain.MODID)
public class GIRCInit {
	
	public static Block RS_ACCEPTOR;
	public static Block RS_EMITTER;
	
	public static Item RS_LINKER;
	
	private static final List<Block> blocks = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerBlock(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		blocks.add(RS_ACCEPTOR = new BlockRedstoneAcceptor());
		blocks.add(RS_EMITTER = new BlockRedstoneEmitter());
		blocks.forEach(registry::register);
	}
	
	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		blocks.forEach(block -> registry.register(new BlockItem(block, new Properties().tab(CreativeModeTab.TAB_REDSTONE)).setRegistryName(block.getRegistryName())));
		registry.register(RS_LINKER = new Linkingtool());
	}
	
	public static BlockEntityType<?> EMITER_TILE;
	
	@SubscribeEvent
	public static void registerTE(RegistryEvent.Register<BlockEntityType<?>> evt) {
		EMITER_TILE = BlockEntityType.Builder.of(TileRedstoneEmitter::new, RS_EMITTER).build(null);
		EMITER_TILE.setRegistryName(GIRCRedstoneMain.MODID, "emitter");
		evt.getRegistry().register(EMITER_TILE);
	}
	
}
