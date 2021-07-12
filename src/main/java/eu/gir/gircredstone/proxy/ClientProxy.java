package eu.gir.gircredstone.proxy;

import eu.gir.gircredstone.init.GIRCInit;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preinit() {
		super.preinit();
		MinecraftForge.EVENT_BUS.register(ClientProxy.class);
	}
	
	@SubscribeEvent
	public static void register(ModelRegistryEvent event) {
		registerM(GIRCInit.RS_LINKER);
		registerM(Item.getItemFromBlock(GIRCInit.RS_ACCEPTOR));
		registerM(Item.getItemFromBlock(GIRCInit.RS_EMITTER));
	}
	
	private static void registerM(final Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0,
				new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
}
