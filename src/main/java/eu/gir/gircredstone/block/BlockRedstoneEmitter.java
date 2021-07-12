package eu.gir.gircredstone.block;

import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRedstoneEmitter extends BlockBasic implements ITileEntityProvider {

	public BlockRedstoneEmitter() {
		super("emitter");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileRedstoneEmitter();
	}

	@Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(world.isRemote)
			return;
		final TileEntity entity = world.getTileEntity(pos);
		if (entity instanceof TileRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
			emitter.redstoneUpdate(world.isBlockPowered(pos));
		}
    }
}
