package com.troblecodings.tcredstone.tile;

import java.util.ArrayList;
import java.util.List;

import com.troblecodings.linkableapi.ILinkableTile;
import com.troblecodings.tcredstone.block.BlockRedstoneAcceptor;
import com.troblecodings.tcredstone.init.GIRCInit;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileRedstoneMultiEmitter extends BlockEntity implements ILinkableTile {

    private final List<BlockPos> listOfPositions = new ArrayList<>();
    
    private static final String ID_X = "xLinkedPos";
    private static final String ID_Y = "yLinkedPos";
    private static final String ID_Z = "zLinkedPos";

    public TileRedstoneMultiEmitter(final BlockPos pos, final BlockState state) {
        super(GIRCInit.MULTI_EMITER_TILE.get(), pos, state);
    }
    
    public static CompoundTag writeBlockPosToNBT(final BlockPos pos, final CompoundTag compound) {
        if (pos != null && compound != null) {
            compound.putInt(ID_X, pos.getX());
            compound.putInt(ID_Y, pos.getY());
            compound.putInt(ID_Z, pos.getZ());
        }
        return compound;
    }

    public static BlockPos readBlockPosFromNBT(final CompoundTag compound) {
        if (compound != null && compound.contains(ID_X) && compound.contains(ID_Y)
                && compound.contains(ID_Z)) {
            return new BlockPos(compound.getInt(ID_X), compound.getInt(ID_Y),
                    compound.getInt(ID_Z));
        }
        return null;
    }

    @Override
    public boolean hasLink() {
        return !listOfPositions.isEmpty();
    }

    @Override
    public boolean link(final BlockPos pos) {
        if (pos != null && !listOfPositions.contains(pos)) {
            listOfPositions.add(pos);
            return true;
        }
        return false;
    }

    @Override
    public boolean unlink() {
        if (listOfPositions.isEmpty())
            return false;
        listOfPositions.clear();
        return true;
    }

    public List<BlockPos> getLinkedPos() {
        return this.listOfPositions;
    }

    @Override
    public void load(final CompoundTag compound) {
        super.load(compound);
        listOfPositions.forEach(BlockPos -> readBlockPosFromNBT(compound));
    }

    @Override
    protected void saveAdditional(final CompoundTag compound) {
        super.saveAdditional(compound);
        listOfPositions.forEach(BlockPos -> writeBlockPosToNBT(BlockPos, compound));
    }
    
    public void redstoneUpdate(final boolean enabled) {
        listOfPositions.forEach(BlockPos -> redstoneUpdate(enabled, BlockPos, level));
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
    
    public static boolean redstoneUpdate(final BlockPos linkedpos, final Level level) {
        if (linkedpos != null) {
            final BlockState state = level.getBlockState(linkedpos);
            if (state.getBlock() instanceof BlockRedstoneAcceptor) {
                final boolean newState = !state.getValue(BlockRedstoneAcceptor.POWER);
                level.setBlock(linkedpos, state.setValue(BlockRedstoneAcceptor.POWER, newState), 3);
                return newState;
            }
        }
        return false;
    }
}
