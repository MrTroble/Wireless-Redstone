package com.troblecodings.tcredstone.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.troblecodings.linkableapi.ILinkableTile;
import com.troblecodings.linkableapi.MultiLinkingTool;
import com.troblecodings.tcredstone.block.BlockRedstoneAcceptor;
import com.troblecodings.tcredstone.init.TCInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileRedstoneMultiEmitter extends BlockEntity implements ILinkableTile {

    private List<BlockPos> listOfPositions = new ArrayList<>();

    private static final String LINKED_POS_LIST = "linkedPos";

    public TileRedstoneMultiEmitter(final BlockPos pos, final BlockState state) {
        super(TCInit.MULTI_EMITER_TILE, pos, state);
    }

    public NbtCompound writeBlockPosToNBT(final List<BlockPos> pos, final NbtCompound compound) {
        if (pos != null && compound != null) {

            final NbtList list = new NbtList();
            listOfPositions.forEach(blockpos -> {
                final NbtElement item = NbtHelper.fromBlockPos(blockpos);
                list.add(item);
            });
            compound.put(LINKED_POS_LIST, list);
        }
        return compound;
    }

    public List<BlockPos> readBlockPosFromNBT(final NbtCompound compound) {
        final NbtList list = (NbtList) compound.get(LINKED_POS_LIST);
        if (list != null) {
            listOfPositions.clear();
            list.forEach(pos -> {
                listOfPositions
                        .add(MultiLinkingTool.toBlockPos((NbtIntArray) pos, LINKED_POS_LIST).get());
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
    public boolean link(final Optional<BlockPos> pos) {
        if (pos != null && !listOfPositions.contains(pos.get())) {
            listOfPositions.add(pos.get());
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
    protected void readNbt(final NbtCompound compound, final WrapperLookup wrapperLookup) {
        super.readNbt(compound, wrapperLookup);
        this.listOfPositions = readBlockPosFromNBT(compound);
    }

    @Override
    protected void writeNbt(final NbtCompound compound, final WrapperLookup wrapperLookup) {
        super.writeNbt(compound, wrapperLookup);
        writeBlockPosToNBT(listOfPositions, compound);
    }

    public void redstoneUpdate(final boolean enabled) {
        listOfPositions.forEach(blockpos -> redstoneUpdate(enabled, blockpos, world));
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
}
