package com.troblecodings.tcredstone.tile;

import java.util.ArrayList;
import java.util.List;

import com.troblecodings.linkableapi.ILinkableTile;
import com.troblecodings.tcredstone.block.BlockRedstoneAcceptor;
import com.troblecodings.tcredstone.init.TCRedstoneInit;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileRedstoneMultiEmitter extends TileEntity implements ILinkableTile {

    private List<BlockPos> listOfPositions = new ArrayList<>();

    private static final String LINKED_POS_LIST = "linkedPos";

    public TileRedstoneMultiEmitter() {
        super(TCRedstoneInit.MULTI_EMITER_TILE.get());
    }

    public CompoundNBT writeBlockPosToNBT(final List<BlockPos> pos, final CompoundNBT compound) {
        if (pos != null && compound != null) {

            final ListNBT list = new ListNBT();
            listOfPositions.forEach(blockpos -> {
                final CompoundNBT item = NBTUtil.writeBlockPos(blockpos);
                list.add(item);
            });
            compound.put(LINKED_POS_LIST, list);
            System.out.println("List: " + listOfPositions);
            System.out.println("Comp: " + compound);
        }
        return compound;
    }

    public List<BlockPos> readBlockPosFromNBT(final CompoundNBT compound) {
        final ListNBT list = (ListNBT) compound.get(LINKED_POS_LIST);
        if (list != null) {
            listOfPositions.clear();
            list.forEach(pos -> {
                final CompoundNBT item = (CompoundNBT) pos;
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
    public void load(final CompoundNBT compound) {
        super.load(compound);
        this.listOfPositions = readBlockPosFromNBT(compound);
    }

    @Override
    public CompoundNBT save(final CompoundNBT compound) {
        super.save(compound);
        writeBlockPosToNBT(listOfPositions, compound);
        return compound;
    }

    public void redstoneUpdate(final boolean enabled) {
        listOfPositions.forEach(blockpos -> redstoneUpdate(enabled, blockpos, level));
    }

    public static boolean redstoneUpdate(final boolean enabled, final BlockPos linkedpos,
            final World level) {
        if (linkedpos != null) {
            final BlockState state = level.getBlockState(linkedpos);
            if (state.getBlock() instanceof BlockRedstoneAcceptor) {
                level.setBlock(linkedpos, state.setValue(BlockRedstoneAcceptor.POWER, enabled), 3);
            }
        }
        return enabled;
    }

    public static boolean redstoneUpdate(final BlockPos linkedpos, final World level) {
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
