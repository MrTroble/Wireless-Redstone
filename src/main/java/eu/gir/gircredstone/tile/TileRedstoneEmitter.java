package eu.gir.gircredstone.tile;

import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.init.GIRCInit;
import eu.gir.gircredstone.item.Linkingtool;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileRedstoneEmitter extends BlockEntity {

	public TileRedstoneEmitter(BlockPos pos, BlockState state) {
		super(GIRCInit.EMITER_TILE, pos, state);
	}

	private BlockPos linkedpos = null;

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		super.deserializeNBT(nbt);
		this.linkedpos = Linkingtool.readBlockPosFromNBT(nbt);
	}

	@Override
	public CompoundTag serializeNBT() {
		return Linkingtool.writeBlockPosToNBT(linkedpos, super.serializeNBT());
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
