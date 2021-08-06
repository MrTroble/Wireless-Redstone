package eu.gir.gircredstone.tile;

import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.init.GIRCInit;
import eu.gir.gircredstone.item.Linkingtool;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileRedstoneEmitter extends TileEntity {

	public TileRedstoneEmitter() {
		super(GIRCInit.EMITER_TILE);
	}

	private BlockPos linkedpos = null;

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		Linkingtool.writeBlockPosToNBT(linkedpos, compound);
		return super.write(compound);
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		this.linkedpos = Linkingtool.readBlockPosFromNBT(compound);
	}

	public boolean link(final BlockPos pos) {
		if (pos == null)
			return false;
		this.linkedpos = pos;
		return true;
	}

	public boolean unlink() {
		if (this.linkedpos == null)
			return false;
		this.linkedpos = null;
		return true;
	}

	public BlockPos getLinkedPos() {
		return this.linkedpos;
	}

	public void redstoneUpdate(final boolean enabled) {
		if (linkedpos != null) {
			final BlockState state = world.getBlockState(linkedpos);
			if (state.getBlock() instanceof BlockRedstoneAcceptor) {
				world.setBlockState(linkedpos, state.with(BlockRedstoneAcceptor.POWER, enabled));
			}
		}
	}

}
