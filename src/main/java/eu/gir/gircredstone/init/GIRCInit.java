package eu.gir.gircredstone.init;

import eu.gir.gircredstone.GIRCRedstoneMain;
import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.block.BlockRedstoneEmitter;
import eu.gir.gircredstone.item.Linkingtool;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
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
		GIRCRedstoneMain.LOGGER.info("Hello!000000000000=========================================000000");
	}

	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		registry.register(new ItemBlock(RS_ACCEPTOR, new Properties().group(ItemGroup.REDSTONE))
				.setRegistryName(RS_ACCEPTOR.getRegistryName()));
		registry.register(RS_LINKER);
		registry.register(new ItemBlock(RS_EMITTER, new Properties().group(ItemGroup.REDSTONE))
				.setRegistryName(RS_EMITTER.getRegistryName()));
	}

	public static final TileEntityType<?> EMITER_TILE = TileEntityType.Builder.create(TileRedstoneEmitter::new).build(null);

	@SubscribeEvent
	public static void registerTE(RegistryEvent.Register<TileEntityType<?>> evt) {
		EMITER_TILE.setRegistryName(GIRCRedstoneMain.MODID, "emitter");
		evt.getRegistry().register(EMITER_TILE);
	}

}
