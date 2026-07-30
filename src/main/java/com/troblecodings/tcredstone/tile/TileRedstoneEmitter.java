package com.troblecodings.tcredstone.tile;

import java.util.Optional;

import com.troblecodings.linkableapi.ILinkableTile;
import com.troblecodings.tcredstone.block.BlockRedstoneAcceptor;
import com.troblecodings.tcredstone.init.TCInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileRedstoneEmitter extends BlockEntity implements ILinkableTile {

    public TileRedstoneEmitter(final BlockPos pos, final BlockState state) {
        super(TCInit.EMITER_TILE, pos, state);
    }

    private BlockPos linkedpos = null;

    private static final String ID_X = "xLinkedPos";
    private static final String ID_Y = "yLinkedPos";
    private static final String ID_Z = "zLinkedPos";

    @Override
    protected void readData(final ReadView view) {
        super.readData(view);
        final Optional<Integer> x = view.getOptionalInt(ID_X);
        final Optional<Integer> y = view.getOptionalInt(ID_Y);
        final Optional<Integer> z = view.getOptionalInt(ID_Z);
        if (x.isPresent() && y.isPresent() && z.isPresent()) {
            this.linkedpos = new BlockPos(x.get(), y.get(), z.get());
        } else {
            this.linkedpos = null;
        }
    }

    @Override
    protected void writeData(final WriteView view) {
        super.writeData(view);
        if (linkedpos != null) {
            view.putInt(ID_X, linkedpos.getX());
            view.putInt(ID_Y, linkedpos.getY());
            view.putInt(ID_Z, linkedpos.getZ());
        }
    }

    @Override
    public boolean link(final Optional<BlockPos> pos) {
        if (pos == null)
            return false;
        this.linkedpos = pos.get();
        return true;
    }

    @Override
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
        redstoneUpdate(enabled, linkedpos, world);
    }

    public static boolean redstoneUpdate(final boolean enabled, final BlockPos linkedpos,
            final World level) {
        if (linkedpos != null) {
            final BlockState state = level.getBlockState(linkedpos);
            if (state.getBlock() instanceof BlockRedstoneAcceptor) {
                level.setBlockState(linkedpos, state.with(BlockRedstoneAcceptor.POWER, enabled), 3);
            }
        }
        return enabled;
    }

    public static boolean redstoneUpdate(final BlockPos linkedpos, final World level) {
        if (linkedpos != null) {
            final BlockState state = level.getBlockState(linkedpos);
            if (state.getBlock() instanceof BlockRedstoneAcceptor) {
                final boolean newState = !state.get(BlockRedstoneAcceptor.POWER);
                level.setBlockState(linkedpos, state.with(BlockRedstoneAcceptor.POWER, newState),
                        3);
                return newState;
            }
        }
        return false;
    }

    @Override
    public boolean hasLink() {
        return this.linkedpos != null;
    }
}
