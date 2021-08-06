package eu.gir.gircredstone.block;

import java.util.UUID;

import eu.gir.gircredstone.init.GIRCInit;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BlockRedstoneEmitter extends BlockBasic implements ITileEntityProvider {

	public BlockRedstoneEmitter() {
		super("emitter");
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return newBlockEntity(world);
	}	
		
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult hit) {
		if (world.isClientSide)
			return ActionResultType.PASS;
		if (player.getItemInHand(hand).getItem().equals(GIRCInit.RS_LINKER))
			return ActionResultType.PASS;
		final TileEntity entity = world.getBlockEntity(pos);
		final UUID uuid = player.getUUID();
		if (entity instanceof TileRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
			final BlockPos linkedpos = emitter.getLinkedPos();
			if (linkedpos == null) {
				player.sendMessage(new TranslationTextComponent("em.notlinked"), uuid);
			} else {
				if (player.isCrouching()) {
					emitter.unlink();
					player.sendMessage(new TranslationTextComponent("em.unlink", linkedpos.getX(), linkedpos.getY(),
							linkedpos.getZ()), uuid);
				} else {
					player.sendMessage(new TranslationTextComponent("lt.linkedpos", linkedpos.getX(), linkedpos.getY(),
							linkedpos.getZ()), uuid);
				}
			}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		if (world.isClientSide)
			return;
		final TileEntity entity = world.getBlockEntity(pos);
		if (entity instanceof TileRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
			emitter.redstoneUpdate(world.hasNeighborSignal(pos));
		}
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
		return new TileRedstoneEmitter();
	}
}
