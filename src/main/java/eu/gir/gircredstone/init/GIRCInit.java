package eu.gir.gircredstone.init;

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

@Mod.EventBusSubscriber(bus=Bus.MOD, modid = GIRCRedstoneMain.MODID)
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
		registry.register(new BlockItem(RS_ACCEPTOR, new Properties().tab(CreativeModeTab.TAB_REDSTONE))
				.setRegistryName(RS_ACCEPTOR.getRegistryName()));
		registry.register(RS_LINKER);
		registry.register(new BlockItem(RS_EMITTER, new Properties().tab(CreativeModeTab.TAB_REDSTONE))
				.setRegistryName(RS_EMITTER.getRegistryName()));
	}

	public static final BlockEntityType<?> EMITER_TILE = BlockEntityType.Builder.of(TileRedstoneEmitter::new, RS_EMITTER).build(null);

	@SubscribeEvent
	public static void registerTE(RegistryEvent.Register<BlockEntityType<?>> evt) {
		EMITER_TILE.setRegistryName(GIRCRedstoneMain.MODID, "emitter");
		evt.getRegistry().register(EMITER_TILE);
	}

}
