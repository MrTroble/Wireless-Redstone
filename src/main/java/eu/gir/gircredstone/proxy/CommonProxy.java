package eu.gir.gircredstone.proxy;

import eu.gir.gircredstone.init.GIRCInit;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	public void preinit() {
		MinecraftForge.EVENT_BUS.register(GIRCInit.class);
		TileEntity.register("rsemitter", TileRedstoneEmitter.class);
	}
	
}
