package eu.gir.gircredstone.item;

import java.util.List;

import com.google.common.collect.Lists;

import eu.gir.gircredstone.GIRCRedstoneMain;
import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.block.BlockRedstoneEmitter;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class Linkingtool extends Item {

	public Linkingtool() {
		this.setCreativeTab(CreativeTabs.REDSTONE);
		this.setRegistryName(GIRCRedstoneMain.MODID, "linker");
		this.setUnlocalizedName(this.getRegistryName().getResourcePath());
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
		final Block block = worldIn.getBlockState(pos).getBlock();
		if (player.isSneaking()) {
			final ItemStack stack = player.getHeldItem(hand);
			if (Linkingtool.readBlockPosFromNBT(stack.getTagCompound()) != null) {
				stack.setTagCompound(null);
				player.sendMessage(new TextComponentString(I18n.format("lt.reset")));
				return EnumActionResult.SUCCESS;
			}
		}
		if (block instanceof BlockRedstoneAcceptor) {
			final NBTTagCompound comp = new NBTTagCompound();
			final ItemStack stack = player.getHeldItem(hand);
			if (readBlockPosFromNBT(stack.getTagCompound()) != null)
				return EnumActionResult.FAIL;
			writeBlockPosToNBT(pos, comp);
			player.getHeldItem(hand).setTagCompound(comp);
			player.sendMessage(new TextComponentString(I18n.format("lt.setpos", pos.getX(), pos.getY(), pos.getZ())
					.replaceAll(String.valueOf((char) 13), "").trim()));
			return EnumActionResult.SUCCESS;
		}
		if (block instanceof BlockRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) worldIn.getTileEntity(pos);
			final ItemStack stack = player.getHeldItem(hand);
			final NBTTagCompound comp = stack.getTagCompound();
			final BlockPos linkpos = Linkingtool.readBlockPosFromNBT(comp);
			if (emitter.link(linkpos)) {
				player.sendMessage(new TextComponentString(
						I18n.format("lt.linkedpos", linkpos.getX(), linkpos.getY(), linkpos.getZ())));
				stack.setTagCompound(null);
				player.sendMessage(new TextComponentString(I18n.format("lt.reset")));
				return EnumActionResult.SUCCESS;
			}
			player.sendMessage(new TextComponentString(
					I18n.format("lt.notlinked").replaceAll(String.valueOf((char) 13), "").trim()));
			return EnumActionResult.FAIL;
		}
		return EnumActionResult.FAIL;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		final NBTTagCompound nbt = stack.getTagCompound();
		if (nbt != null) {
			final BlockPos pos = Linkingtool.readBlockPosFromNBT(nbt);
			if (pos != null) {
				tooltip.add(I18n.format("lt.linkedpos", pos.getX(), pos.getY(), pos.getZ()));
				return;
			}
		}
		tooltip.addAll(Lists.newArrayList(
				I18n.format("lt.notlinked").replaceAll(String.valueOf((char) 13), "").trim().split("\n")));
	}

}
