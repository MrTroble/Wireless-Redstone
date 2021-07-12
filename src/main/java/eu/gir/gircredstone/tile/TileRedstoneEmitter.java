package eu.gir.gircredstone.tile;

import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.item.Linkingtool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileRedstoneEmitter extends TileEntity {

	private BlockPos linkedpos = null;

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		Linkingtool.writeBlockPosToNBT(linkedpos, compound);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.linkedpos = Linkingtool.readBlockPosFromNBT(compound);
	}

	public void link(final BlockPos pos) {
		this.linkedpos = pos;
	}

	public void redstoneUpdate(final boolean enabled) {
		if (linkedpos != null) {
			System.out.println("RSUP: " + enabled);
			final IBlockState state = world.getBlockState(linkedpos);
			if (state.getBlock() instanceof BlockRedstoneAcceptor) {
				world.setBlockState(linkedpos, state.withProperty(BlockRedstoneAcceptor.POWER, enabled));
			}
		}
	}

}
