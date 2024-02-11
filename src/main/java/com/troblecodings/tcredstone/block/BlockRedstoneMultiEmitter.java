package com.troblecodings.tcredstone.block;

import java.util.List;

import com.troblecodings.linkableapi.Message;
import com.troblecodings.tcredstone.init.TCInit;
import com.troblecodings.tcredstone.tile.TileRedstoneMultiEmitter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlockRedstoneMultiEmitter extends BlockRedstoneEmitter implements Message {

	public BlockRedstoneMultiEmitter(final Settings properties) {
		super(properties);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView var1) {
		return new TileRedstoneMultiEmitter();
	}
	
	@Override
	public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player,
	        Hand hand, BlockHitResult hit) {
	    if (world.isClient())
            return false;
        if (player.getStackInHand(hand).getItem().equals(TCInit.RS_LINKER)
                || player.getStackInHand(hand).getItem().equals(TCInit.RS_MULTILINKER))
            return false;
        final BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof TileRedstoneMultiEmitter) {
            final TileRedstoneMultiEmitter emitter = (TileRedstoneMultiEmitter) entity;
            final List<BlockPos> listOfPositions = emitter.getLinkedPos();
            if (listOfPositions == null) {
                message(player, "em.notlinked");
            } else {
                if (player.isSneaking()) {
                    emitter.unlink();
                    listOfPositions.forEach(blockpos -> message(player, "em.unlink", blockpos.getX(), blockpos.getY(),
                            blockpos.getZ()));
                } else {
                    listOfPositions.forEach(blockpos -> message(player, "lt.linkedpos", blockpos.getX(),
                            blockpos.getY(), blockpos.getZ()));
                }
            }
            return true;
        }
        return false;
	}

	@Override
	public void neighborUpdate(final BlockState state, final World world, final BlockPos pos, final Block blockIn,
			final BlockPos fromPos, final boolean isMoving) {
		if (world.isClient())
			return;
		final BlockEntity entity = world.getBlockEntity(pos);
		if (entity instanceof TileRedstoneMultiEmitter) {
			final TileRedstoneMultiEmitter emitter = (TileRedstoneMultiEmitter) entity;
			emitter.redstoneUpdate(world.isReceivingRedstonePower(pos));
		}
	}

}
