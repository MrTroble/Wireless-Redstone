package com.troblecodings.tcredstone.tile;

import java.util.Optional;

import com.troblecodings.linkableapi.ILinkableTile;
import com.troblecodings.tcredstone.block.BlockRedstoneAcceptor;
import com.troblecodings.tcredstone.init.TCInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
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

    public static NbtCompound writeBlockPosToNBT(final BlockPos pos, final NbtCompound compound) {
        if (pos != null && compound != null) {
            compound.putInt(ID_X, pos.getX());
            compound.putInt(ID_Y, pos.getY());
            compound.putInt(ID_Z, pos.getZ());
        }
        return compound;
    }

    public static BlockPos readBlockPosFromNBT(final NbtCompound compound) {
        if (compound != null && compound.contains(ID_X) && compound.contains(ID_Y)
                && compound.contains(ID_Z))
            return new BlockPos(compound.getInt(ID_X, 0), compound.getInt(ID_Y, 0),
                    compound.getInt(ID_Z, 0));
        return null;
    }

    @Override
    protected void readNbt(final NbtCompound compound, final WrapperLookup wrapperLookup) {
        super.readNbt(compound, wrapperLookup);
        this.linkedpos = readBlockPosFromNBT(compound);
    }

    @Override
    protected void writeNbt(final NbtCompound compound, final WrapperLookup wrapperLookup) {
        super.writeNbt(compound, wrapperLookup);
        writeBlockPosToNBT(linkedpos, compound);
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
