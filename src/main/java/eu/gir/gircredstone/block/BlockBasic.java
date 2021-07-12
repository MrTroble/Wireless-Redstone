package eu.gir.gircredstone.block;

import eu.gir.gircredstone.GIRCRedstoneMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockBasic extends Block {

	public BlockBasic(final String name) {
		super(Material.ROCK);
		this.setRegistryName(GIRCRedstoneMain.MODID, name);
		this.setCreativeTab(CreativeTabs.REDSTONE);
	}

}
