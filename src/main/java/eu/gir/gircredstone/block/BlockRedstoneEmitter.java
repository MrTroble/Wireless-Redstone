package eu.gir.gircredstone.block;

import java.util.UUID;

import eu.gir.gircredstone.init.GIRCInit;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlockRedstoneEmitter extends BlockBasic implements EntityBlock {

	public BlockRedstoneEmitter() {
		super("emitter");
	}
		
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult hit) {
		if (world.isClientSide)
			return InteractionResult.PASS;
		if (player.getItemInHand(hand).getItem().equals(GIRCInit.RS_LINKER))
			return InteractionResult.PASS;
		final BlockEntity entity = world.getBlockEntity(pos);
		final UUID uuid = player.getUUID();
		if (entity instanceof TileRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
			final BlockPos linkedpos = emitter.getLinkedPos();
			if (linkedpos == null) {
				player.sendMessage(new TranslatableComponent("em.notlinked"), uuid);
			} else {
				if (player.isCrouching()) {
					emitter.unlink();
					player.sendMessage(new TranslatableComponent("em.unlink", linkedpos.getX(), linkedpos.getY(),
							linkedpos.getZ()), uuid);
				} else {
					player.sendMessage(new TranslatableComponent("lt.linkedpos", linkedpos.getX(), linkedpos.getY(),
							linkedpos.getZ()), uuid);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.FAIL;
	}

	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		if (world.isClientSide)
			return;
		final BlockEntity entity = world.getBlockEntity(pos);
		if (entity instanceof TileRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
			emitter.redstoneUpdate(world.hasNeighborSignal(pos));
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos arg0, BlockState arg1) {
		return new TileRedstoneEmitter(arg0, arg1);
	}
}
