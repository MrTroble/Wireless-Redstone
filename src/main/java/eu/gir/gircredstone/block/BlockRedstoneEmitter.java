package eu.gir.gircredstone.block;

import eu.gir.gircredstone.init.GIRCInit;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
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
		return createNewTileEntity(world);
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult hit) {
		if (world.isRemote)
			return true;
		if (player.getHeldItem(hand).getItem().equals(GIRCInit.RS_LINKER))
			return false;
		final TileEntity entity = world.getTileEntity(pos);
		if (entity instanceof TileRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
			final BlockPos linkedpos = emitter.getLinkedPos();
			if (linkedpos == null) {
				player.sendMessage(new TranslationTextComponent("em.notlinked"));
			} else {
				if (player.isSneaking()) {
					emitter.unlink();
					player.sendMessage(new TranslationTextComponent("em.unlink", linkedpos.getX(), linkedpos.getY(),
							linkedpos.getZ()));
				} else {
					player.sendMessage(new TranslationTextComponent("lt.linkedpos", linkedpos.getX(), linkedpos.getY(),
							linkedpos.getZ()));
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		if (world.isRemote)
			return;
		final TileEntity entity = world.getTileEntity(pos);
		if (entity instanceof TileRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
			emitter.redstoneUpdate(world.isBlockPowered(pos));
		}
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TileRedstoneEmitter();
	}
}
