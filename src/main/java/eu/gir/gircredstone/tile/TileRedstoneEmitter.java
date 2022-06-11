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
	public void load(final CompoundTag nbt) {
		super.load(nbt);
		this.linkedpos = Linkingtool.readBlockPosFromNBT(nbt);
	}
	
	@Override
	public CompoundTag save(final CompoundTag nbt) {
		super.save(nbt);
		Linkingtool.writeBlockPosToNBT(linkedpos, nbt);
		return nbt;
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
			final BlockState state = level.getBlockState(linkedpos);
			if (state.getBlock() instanceof BlockRedstoneAcceptor) {
				level.setBlock(linkedpos, state.setValue(BlockRedstoneAcceptor.POWER, enabled), 3);
			}
		}
	}

}
