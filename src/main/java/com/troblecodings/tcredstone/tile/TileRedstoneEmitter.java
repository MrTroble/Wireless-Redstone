package com.troblecodings.tcredstone.tile;

import com.troblecodings.linkableapi.ILinkableTile;
import com.troblecodings.tcredstone.block.BlockRedstoneAcceptor;
import com.troblecodings.tcredstone.init.TCRedstoneInit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileRedstoneEmitter extends TileEntity implements ILinkableTile {

    public TileRedstoneEmitter() {
        super(TCRedstoneInit.EMITER_TILE);
    }

    private BlockPos linkedpos = null;

    private static final String ID_X = "xLinkedPos";
    private static final String ID_Y = "yLinkedPos";
    private static final String ID_Z = "zLinkedPos";

    public static NBTTagCompound writeBlockPosToNBT(final BlockPos pos, final NBTTagCompound compound) {
        if (pos != null && compound != null) {
            compound.setInt(ID_X, pos.getX());
            compound.setInt(ID_Y, pos.getY());
            compound.setInt(ID_Z, pos.getZ());
        }
        return compound;
    }

    public static BlockPos readBlockPosFromNBT(final NBTTagCompound compound) {
        if (compound != null && compound.hasKey(ID_X) && compound.hasKey(ID_Y)
                && compound.hasKey(ID_Z)) {
            return new BlockPos(compound.getInt(ID_X), compound.getInt(ID_Y),
                    compound.getInt(ID_Z));
        }
        return null;
    }

    @Override
    public void read(final NBTTagCompound compound) {
        super.read(compound);
        this.linkedpos = readBlockPosFromNBT(compound);
    }

    @Override
    public NBTTagCompound write(final NBTTagCompound compound) {
        super.write(compound);
        writeBlockPosToNBT(linkedpos, compound);
        return super.write(compound);
    }

    @Override
    public boolean link(final BlockPos pos) {
        if (pos == null)
            return false;
        this.linkedpos = pos;
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
            final World world) {
        if (linkedpos != null) {
            final IBlockState state = world.getBlockState(linkedpos);
            if (state.getBlock() instanceof BlockRedstoneAcceptor) {
                world.setBlockState(linkedpos, state.with(BlockRedstoneAcceptor.POWER, enabled), 3);
            }
        }
        return enabled;
    }

    public static boolean redstoneUpdate(final BlockPos linkedpos, final World level) {
        if (linkedpos != null) {
            final IBlockState state = level.getBlockState(linkedpos);
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
