package com.troblecodings.tcredstone.tile;

import java.util.ArrayList;
import java.util.List;

import com.troblecodings.linkableapi.ILinkableTile;
import com.troblecodings.tcredstone.block.BlockRedstoneAcceptor;
import com.troblecodings.tcredstone.init.TCRedstoneInit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileRedstoneMultiEmitter extends TileEntity implements ILinkableTile {

    private List<BlockPos> listOfPositions = new ArrayList<>();

    private static final String LINKED_POS_LIST = "linkedPos";

    public TileRedstoneMultiEmitter() {
        super(TCRedstoneInit.MULTI_EMITER_TILE);
    }

    public NBTTagCompound writeBlockPosToNBT(final List<BlockPos> pos,
            final NBTTagCompound compound) {
        if (pos != null && compound != null) {

            final NBTTagList list = new NBTTagList();
            listOfPositions.forEach(blockpos -> {
                final NBTTagCompound item = NBTUtil.writeBlockPos(blockpos);
                list.add(item);
            });
            compound.setTag(LINKED_POS_LIST, list);
        }
        return compound;
    }

    public List<BlockPos> readBlockPosFromNBT(final NBTTagCompound compound) {
        final NBTTagList list = (NBTTagList) compound.getTag(LINKED_POS_LIST);
        if (list != null) {
            listOfPositions.clear();
            list.forEach(pos -> {
                final NBTTagCompound item = (NBTTagCompound) pos;
                listOfPositions.add(NBTUtil.readBlockPos(item));
            });
            return listOfPositions;
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
    public void read(final NBTTagCompound compound) {
        super.read(compound);
        this.listOfPositions = readBlockPosFromNBT(compound);
    }

    @Override
    public NBTTagCompound write(final NBTTagCompound compound) {
        super.write(compound);
        writeBlockPosToNBT(listOfPositions, compound);
        return compound;
    }

    public void redstoneUpdate(final boolean enabled) {
        listOfPositions.forEach(blockpos -> redstoneUpdate(enabled, blockpos, world));
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

    public static boolean redstoneUpdate(final BlockPos linkedpos, final World world) {
        if (linkedpos != null) {
            final IBlockState state = world.getBlockState(linkedpos);
            if (state.getBlock() instanceof BlockRedstoneAcceptor) {
                final boolean newState = !state.has(BlockRedstoneAcceptor.POWER);
                world.setBlockState(linkedpos, state.with(BlockRedstoneAcceptor.POWER, newState),
                        3);
                return newState;
            }
        }
        return false;
    }
}
