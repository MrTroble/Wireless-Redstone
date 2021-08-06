package eu.gir.gircredstone.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import eu.gir.gircredstone.GIRCRedstoneMain;
import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.block.BlockRedstoneEmitter;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Linkingtool extends Item {

	public Linkingtool() {
		super(new Properties().tab(ItemGroup.TAB_REDSTONE));
		this.setRegistryName(GIRCRedstoneMain.MODID, "linker");
	}

	private static final String ID_X = "xLinkedPos";
	private static final String ID_Y = "yLinkedPos";
	private static final String ID_Z = "zLinkedPos";

	public static CompoundNBT writeBlockPosToNBT(BlockPos pos, CompoundNBT compound) {
		if (pos != null && compound != null) {
			compound.putInt(ID_X, pos.getX());
			compound.putInt(ID_Y, pos.getY());
			compound.putInt(ID_Z, pos.getZ());
		}
		return compound;
	}

	public static BlockPos readBlockPosFromNBT(CompoundNBT compound) {
		if (compound != null && compound.contains(ID_X) && compound.contains(ID_Y) && compound.contains(ID_Z)) {
			return new BlockPos(compound.getInt(ID_X), compound.getInt(ID_Y), compound.getInt(ID_Z));
		}
		return null;
	}
	
	@Override
	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext ctx) {
		final World worldIn = ctx.getLevel();
		final PlayerEntity player = ctx.getPlayer();
		final BlockPos pos = ctx.getClickedPos();
		if (worldIn.isClientSide)
			return ActionResultType.PASS;
		final Block block = worldIn.getBlockState(pos).getBlock();
		final UUID uuid = player.getUUID();
		if (player.isCrouching()) {
			if (Linkingtool.readBlockPosFromNBT(stack.getTag()) != null) {
				stack.setTag(null);
				player.sendMessage(new TranslationTextComponent("lt.reset"), uuid);
				return ActionResultType.SUCCESS;
			}
		}
		if (block instanceof BlockRedstoneAcceptor) {
			final CompoundNBT comp = new CompoundNBT();
			if (readBlockPosFromNBT(stack.getTag()) != null)
				return ActionResultType.FAIL;
			writeBlockPosToNBT(pos, comp);
			stack.setTag(comp);
			player.sendMessage(new TranslationTextComponent("lt.setpos", pos.getX(), pos.getY(), pos.getZ()), uuid);
			player.sendMessage(new TranslationTextComponent("lt.setpos.msg"), uuid);
			return ActionResultType.SUCCESS;
		}
		if (block instanceof BlockRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) worldIn.getBlockEntity(pos);
			final CompoundNBT comp = stack.getTag();
			final BlockPos linkpos = Linkingtool.readBlockPosFromNBT(comp);
			if (emitter.link(linkpos)) {
				player.sendMessage(
						new TranslationTextComponent("lt.linkedpos", linkpos.getX(), linkpos.getY(), linkpos.getZ()), uuid);
				stack.setTag(null);
				player.sendMessage(new TranslationTextComponent("lt.reset"), uuid);
				return ActionResultType.SUCCESS;
			}
			player.sendMessage(new TranslationTextComponent("lt.notlinked"), uuid);
			player.sendMessage(new TranslationTextComponent("lt.notlinked.msg"), uuid);
			return ActionResultType.FAIL;
		}
		return ActionResultType.FAIL;
	}
		
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		final CompoundNBT nbt = stack.getTag();
		if (nbt != null) {
			final BlockPos pos = Linkingtool.readBlockPosFromNBT(nbt);
			if (pos != null) {
				tooltip.add(new TranslationTextComponent("lt.linkedpos", pos.getX(), pos.getY(), pos.getZ()));
				return;
			}
		}
		tooltip.add(new TranslationTextComponent("lt.notlinked"));
		tooltip.add(new TranslationTextComponent("lt.notlinked.msg"));
	}

}
