package eu.gir.gircredstone.block;

import eu.gir.gircredstone.GIRCRedstoneMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBasic extends Block {

	public BlockBasic(final String name) {
		super(Properties.create(Material.MISCELLANEOUS));
		this.setRegistryName(GIRCRedstoneMain.MODID, name);
	}

}
