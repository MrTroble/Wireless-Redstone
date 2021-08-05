package eu.gir.gircredstone.item;

import java.util.List;

import javax.annotation.Nullable;

import eu.gir.gircredstone.GIRCRedstoneMain;
import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.block.BlockRedstoneEmitter;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Linkingtool extends Item {

	public Linkingtool() {
		super(new Properties().group(ItemGroup.REDSTONE));
		this.setRegistryName(GIRCRedstoneMain.MODID, "linker");
	}

	private static final String ID_X = "xLinkedPos";
	private static final String ID_Y = "yLinkedPos";
	private static final String ID_Z = "zLinkedPos";

	public static void writeBlockPosToNBT(BlockPos pos, NBTTagCompound compound) {
		if (pos != null && compound != null) {
			compound.setInt(ID_X, pos.getX());
			compound.setInt(ID_Y, pos.getY());
			compound.setInt(ID_Z, pos.getZ());
		}
	}

	public static BlockPos readBlockPosFromNBT(NBTTagCompound compound) {
		if (compound != null && compound.hasKey(ID_X) && compound.hasKey(ID_Y) && compound.hasKey(ID_Z)) {
			return new BlockPos(compound.getInt(ID_X), compound.getInt(ID_Y), compound.getInt(ID_Z));
		}
		return null;
	}

	@Override
	public EnumActionResult onItemUse(ItemUseContext ctx) {
		final World worldIn = ctx.getWorld();
		final EntityPlayer player = ctx.getPlayer();
		final BlockPos pos = ctx.getPos();
		if (worldIn.isRemote)
			return EnumActionResult.PASS;
		final Block block = worldIn.getBlockState(pos).getBlock();
		final ItemStack stack = ctx.getItem();
		if (player.isSneaking()) {
			if (Linkingtool.readBlockPosFromNBT(stack.getTag()) != null) {
				stack.setTag(null);
				player.sendMessage(new TextComponentTranslation("lt.reset"));
				return EnumActionResult.SUCCESS;
			}
		}
		if (block instanceof BlockRedstoneAcceptor) {
			final NBTTagCompound comp = new NBTTagCompound();
			if (readBlockPosFromNBT(stack.getTag()) != null)
				return EnumActionResult.FAIL;
			writeBlockPosToNBT(pos, comp);
			stack.setTag(comp);
			player.sendMessage(new TextComponentTranslation("lt.setpos", pos.getX(), pos.getY(), pos.getZ()));
			player.sendMessage(new TextComponentTranslation("lt.setpos.msg"));
			return EnumActionResult.SUCCESS;
		}
		if (block instanceof BlockRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) worldIn.getTileEntity(pos);
			final NBTTagCompound comp = stack.getTag();
			final BlockPos linkpos = Linkingtool.readBlockPosFromNBT(comp);
			if (emitter.link(linkpos)) {
				player.sendMessage(
						new TextComponentTranslation("lt.linkedpos", linkpos.getX(), linkpos.getY(), linkpos.getZ()));
				stack.setTag(null);
				player.sendMessage(new TextComponentTranslation("lt.reset"));
				return EnumActionResult.SUCCESS;
			}
			player.sendMessage(new TextComponentTranslation("lt.notlinked"));
			player.sendMessage(new TextComponentTranslation("lt.notlinked.msg"));
			return EnumActionResult.FAIL;
		}
		return EnumActionResult.FAIL;
	}
		
	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		final NBTTagCompound nbt = stack.getTag();
		if (nbt != null) {
			final BlockPos pos = Linkingtool.readBlockPosFromNBT(nbt);
			if (pos != null) {
				tooltip.add(new TextComponentTranslation("lt.linkedpos", pos.getX(), pos.getY(), pos.getZ()));
				return;
			}
		}
		tooltip.add(new TextComponentTranslation("lt.notlinked"));
	}

}
