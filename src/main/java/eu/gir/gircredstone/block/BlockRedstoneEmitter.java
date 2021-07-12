package eu.gir.gircredstone.block;

import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRedstoneEmitter extends BlockBasic implements ITileEntityProvider {

	public BlockRedstoneEmitter() {
		super("emitter");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileRedstoneEmitter();
	}

}
