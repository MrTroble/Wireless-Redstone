package com.troblecodings.tcredstone.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mojang.serialization.Codec;

import com.troblecodings.linkableapi.ILinkableTile;
import com.troblecodings.tcredstone.block.BlockRedstoneAcceptor;
import com.troblecodings.tcredstone.init.TCInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileRedstoneMultiEmitter extends BlockEntity implements ILinkableTile {

    private List<BlockPos> listOfPositions = new ArrayList<>();

    private static final String LINKED_POS_LIST = "linkedPos";
    private static final Codec<List<BlockPos>> LINKED_POS_CODEC = BlockPos.CODEC.listOf();

    public TileRedstoneMultiEmitter(final BlockPos pos, final BlockState state) {
        super(TCInit.MULTI_EMITER_TILE, pos, state);
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
    protected void readData(final ReadView view) {
        super.readData(view);
        this.listOfPositions =
                new ArrayList<>(view.read(LINKED_POS_LIST, LINKED_POS_CODEC).orElse(List.of()));
    }

    @Override
    protected void writeData(final WriteView view) {
        super.writeData(view);
        if (!listOfPositions.isEmpty()) {
            view.put(LINKED_POS_LIST, LINKED_POS_CODEC, listOfPositions);
        }
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
}
