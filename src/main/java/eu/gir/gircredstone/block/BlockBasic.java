package eu.gir.gircredstone.block;

import eu.gir.gircredstone.GIRCRedstoneMain;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class BlockBasic extends Block {
	
	public BlockBasic(final String name) {
		super(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F));
		this.setRegistryName(GIRCRedstoneMain.MODID, name);
	}
	
}
