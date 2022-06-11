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
	public void load(final BlockState p_230337_1_, final CompoundNBT p_230337_2_) {
		super.load(p_230337_1_, p_230337_2_);
		this.linkedpos = Linkingtool.readBlockPosFromNBT(p_230337_2_);
	}
	
	@Override
	public CompoundNBT save(final CompoundNBT p_189515_1_) {
		Linkingtool.writeBlockPosToNBT(linkedpos, p_189515_1_);
		return super.save(p_189515_1_);
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
