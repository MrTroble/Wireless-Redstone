package com.troblecodings.tcredstone.tile;

import java.util.Optional;

import com.troblecodings.linkableapi.ILinkableTile;
import com.troblecodings.tcredstone.block.BlockRedstoneAcceptor;
import com.troblecodings.tcredstone.init.GIRCInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class TileRedstoneEmitter extends BlockEntity implements ILinkableTile {

    public TileRedstoneEmitter(final BlockPos pos, final BlockState state) {
        super(GIRCInit.EMITER_TILE.get(), pos, state);
    }

    private BlockPos linkedpos = null;

    private static final String ID_X = "xLinkedPos";
    private static final String ID_Y = "yLinkedPos";
    private static final String ID_Z = "zLinkedPos";

    @Override
    protected void loadAdditional(final ValueInput input) {
        super.loadAdditional(input);
        final Optional<Integer> x = input.getInt(ID_X);
        final Optional<Integer> y = input.getInt(ID_Y);
        final Optional<Integer> z = input.getInt(ID_Z);
        if (x.isPresent() && y.isPresent() && z.isPresent()) {
            this.linkedpos = new BlockPos(x.get(), y.get(), z.get());
        } else {
            this.linkedpos = null;
        }
    }

    @Override
    protected void saveAdditional(final ValueOutput output) {
        super.saveAdditional(output);
        if (linkedpos != null) {
            output.putInt(ID_X, linkedpos.getX());
            output.putInt(ID_Y, linkedpos.getY());
            output.putInt(ID_Z, linkedpos.getZ());
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
        redstoneUpdate(enabled, linkedpos, level);
    }

    public static boolean redstoneUpdate(final boolean enabled, final BlockPos linkedpos,
            final Level level) {
        if (linkedpos != null) {
            final BlockState state = level.getBlockState(linkedpos);
            if (state.getBlock() instanceof BlockRedstoneAcceptor) {
                level.setBlock(linkedpos, state.setValue(BlockRedstoneAcceptor.POWER, enabled), 3);
            }
        }
        return enabled;
    }

    public static boolean redstoneUpdate(final Optional<BlockPos> linkpos, final Level level) {
        if (linkpos != null) {
            final BlockState state = level.getBlockState(linkpos.get());
            if (state.getBlock() instanceof BlockRedstoneAcceptor) {
                final boolean newState = !state.getValue(BlockRedstoneAcceptor.POWER);
                level.setBlock(linkpos.get(), state.setValue(BlockRedstoneAcceptor.POWER, newState),
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
