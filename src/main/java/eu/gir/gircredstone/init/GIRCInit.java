package eu.gir.gircredstone.init;

import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.block.BlockRedstoneEmitter;
import eu.gir.gircredstone.item.Linkingtool;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class GIRCInit {

	public static final Block RS_ACCEPTOR = new BlockRedstoneAcceptor();
	public static final Block RS_EMITTER = new BlockRedstoneEmitter();

	public static final Item RS_LINKER = new Linkingtool();
	
	@SubscribeEvent
	public static void registerBlock(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		registry.register(RS_ACCEPTOR);
		registry.register(RS_EMITTER);
	}

	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		registry.register(new ItemBlock(RS_ACCEPTOR).setRegistryName(RS_ACCEPTOR.getRegistryName()));
		registry.register(RS_LINKER);
		registry.register(new ItemBlock(RS_EMITTER).setRegistryName(RS_EMITTER.getRegistryName()));
	}

	
}
