package eu.gir.gircredstone.tile;

import java.util.concurrent.ExecutionException;

import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.init.GIRCInit;
import eu.gir.gircredstone.item.Linkingtool;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerChunkProvider;

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
		if(pos == null)
			return false;
		this.linkedpos = pos;
		return true;
	}
	
	public boolean unlink() {
		if(this.linkedpos == null)
			return false;
		this.linkedpos = null;
		return true;
	}
	
	public BlockPos getLinkedPos() {
		return this.linkedpos;
	}
	
	public void accept(final boolean enabled) {
		final BlockState state = world.getBlockState(linkedpos);
		if (state.getBlock() instanceof BlockRedstoneAcceptor) {
			world.setBlockState(linkedpos, state.with(BlockRedstoneAcceptor.POWER, enabled));
		}
	}

	@SuppressWarnings("deprecation")
	public void redstoneUpdate(final boolean enabled) {
		if (linkedpos != null) {
			final boolean flag = !world.isBlockLoaded(linkedpos);
			if(flag) {
				final Chunk chunk = world.getChunkAt(linkedpos);
				final ServerChunkProvider provider = (ServerChunkProvider) world.getChunkProvider();
				try {
					provider.chunkManager.func_219188_b(chunk.getPos()).get().ifLeft(ch -> {
						accept(enabled);
						try {
							provider.chunkManager.func_222973_a(chunk).get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				return;
			}
			accept(enabled);
		}
	}

}
