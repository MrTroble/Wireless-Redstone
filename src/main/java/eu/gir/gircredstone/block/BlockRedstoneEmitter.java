package eu.gir.gircredstone.block;

import eu.gir.gircredstone.init.GIRCInit;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockRedstoneEmitter extends BlockBasic implements ITileEntityProvider {

	public BlockRedstoneEmitter() {
		super("emitter");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileRedstoneEmitter();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		if(player.getHeldItem(hand).getItem().equals(GIRCInit.RS_LINKER))
			return false;
		final TileEntity entity = world.getTileEntity(pos);
		if (entity instanceof TileRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
			final BlockPos linkedpos = emitter.getLinkedPos();
			if (linkedpos == null) {
				player.sendMessage(new TextComponentTranslation("em.notlinked"));
			} else {
				if (player.isSneaking()) {
					emitter.unlink();
					player.sendMessage(new TextComponentTranslation("em.unlink", linkedpos.getX(), linkedpos.getY(), linkedpos.getZ()));
				} else {
					player.sendMessage(new TextComponentTranslation("lt.linkedpos", linkedpos.getX(), linkedpos.getY(), linkedpos.getZ()));
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (world.isRemote)
			return;
		final TileEntity entity = world.getTileEntity(pos);
		if (entity instanceof TileRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
			emitter.redstoneUpdate(world.isBlockPowered(pos));
		}
	}
}
