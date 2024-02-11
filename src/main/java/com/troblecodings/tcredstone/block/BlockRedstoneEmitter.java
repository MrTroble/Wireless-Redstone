package com.troblecodings.tcredstone.block;

import com.troblecodings.linkableapi.Message;
import com.troblecodings.tcredstone.init.TCInit;
import com.troblecodings.tcredstone.tile.TileRedstoneEmitter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlockRedstoneEmitter extends Block implements BlockEntityProvider, Message {

	public BlockRedstoneEmitter(final Settings properties) {
		super(properties);
	}

	@Override
	public ActionResult onUse(final BlockState state, final World world, final BlockPos pos, final PlayerEntity player,
			final Hand hand, final BlockHitResult hit) {
		if (world.isClient())
			return ActionResult.PASS;
		if (player.getStackInHand(hand).getItem().equals(TCInit.RS_LINKER)
				|| player.getStackInHand(hand).getItem().equals(TCInit.RS_MULTILINKER))
			return ActionResult.PASS;
		final BlockEntity entity = world.getBlockEntity(pos);
		if (entity instanceof TileRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
			final BlockPos linkedpos = emitter.getLinkedPos();
			if (linkedpos == null) {
				message(player, "em.notlinked");
			} else {
				if (player.isSneaking()) {
					emitter.unlink();
					message(player, "em.unlink", linkedpos.getX(), linkedpos.getY(), linkedpos.getZ());
				} else {
					message(player, "lt.linkedpos", linkedpos.getX(), linkedpos.getY(), linkedpos.getZ());
				}
			}
			return ActionResult.SUCCESS;
		}
		return ActionResult.FAIL;
	}

	@Override
	public void neighborUpdate(final BlockState state, final World world, final BlockPos pos, final Block blockIn,
			final BlockPos fromPos, final boolean isMoving) {
		if (world.isClient())
			return;
		final BlockEntity entity = world.getBlockEntity(pos);
		if (entity instanceof TileRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
			emitter.redstoneUpdate(world.isReceivingRedstonePower(pos));
		}
	}

    @Override
    public BlockEntity createBlockEntity(BlockView var1) {
        return new TileRedstoneEmitter();
    }

}
