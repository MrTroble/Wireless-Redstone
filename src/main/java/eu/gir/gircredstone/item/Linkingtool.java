package eu.gir.gircredstone.item;

import eu.gir.gircredstone.GIRCRedstoneMain;
import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.block.BlockRedstoneEmitter;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class Linkingtool extends Item {

	public Linkingtool() {
		this.setCreativeTab(CreativeTabs.REDSTONE);
		this.setRegistryName(GIRCRedstoneMain.MODID, "linker");
	}
	
	private static final String ID_X = "xLinkedPos";
	private static final String ID_Y = "yLinkedPos";
	private static final String ID_Z = "zLinkedPos";

	public static void writeBlockPosToNBT(BlockPos pos, NBTTagCompound compound) {
		if (pos != null && compound != null) {
			compound.setInteger(ID_X, pos.getX());
			compound.setInteger(ID_Y, pos.getY());
			compound.setInteger(ID_Z, pos.getZ());
		}
	}
	
	public static BlockPos readBlockPosFromNBT(NBTTagCompound compound) {
		if (compound != null && compound.hasKey(ID_X) && compound.hasKey(ID_Y) && compound.hasKey(ID_Z)) {
			return new BlockPos(compound.getInteger(ID_X), compound.getInteger(ID_Y), compound.getInteger(ID_Z));
		}
		return null;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return EnumActionResult.PASS;
		Block block = worldIn.getBlockState(pos).getBlock();
		if (block instanceof BlockRedstoneAcceptor) {
			NBTTagCompound comp = new NBTTagCompound();
			writeBlockPosToNBT(pos, comp);
			player.getHeldItem(hand).setTagCompound(comp);
			player.sendMessage(new TextComponentTranslation("lt.added"));
			return EnumActionResult.SUCCESS;
		} else if (block instanceof BlockRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) worldIn.getTileEntity(pos);
			final NBTTagCompound comp = player.getHeldItem(hand).getTagCompound();
			emitter.link(Linkingtool.readBlockPosFromNBT(comp));
			player.sendMessage(new TextComponentTranslation("lt.linked"));
			return EnumActionResult.SUCCESS;
		}
		player.sendMessage(new TextComponentTranslation("lt.notusable"));
		return EnumActionResult.FAIL;
	}

}
