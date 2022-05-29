package eu.gir.gircredstone.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.block.BlockRedstoneEmitter;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class Linkingtool extends Item {
	
	public Linkingtool(final Properties p_41383_) {
		super(p_41383_);
	}
	
	private static final String ID_X = "xLinkedPos";
	private static final String ID_Y = "yLinkedPos";
	private static final String ID_Z = "zLinkedPos";
	
	public static CompoundTag writeBlockPosToNBT(final BlockPos pos, final CompoundTag compound) {
		if (pos != null && compound != null) {
			compound.putInt(ID_X, pos.getX());
			compound.putInt(ID_Y, pos.getY());
			compound.putInt(ID_Z, pos.getZ());
		}
		return compound;
	}
	
	public static BlockPos readBlockPosFromNBT(final CompoundTag compound) {
		if (compound != null && compound.contains(ID_X) && compound.contains(ID_Y) && compound.contains(ID_Z)) {
			return new BlockPos(compound.getInt(ID_X), compound.getInt(ID_Y), compound.getInt(ID_Z));
		}
		return null;
	}
	
	@Override
	public InteractionResult onItemUseFirst(final ItemStack stack, final UseOnContext ctx) {
		final Level LevelIn = ctx.getLevel();
		final Player player = ctx.getPlayer();
		final BlockPos pos = ctx.getClickedPos();
		if (LevelIn.isClientSide)
			return InteractionResult.PASS;
		final Block block = LevelIn.getBlockState(pos).getBlock();
		final UUID uuid = player.getUUID();
		if (player.isCrouching()) {
			if (Linkingtool.readBlockPosFromNBT(stack.getTag()) != null) {
				stack.setTag(null);
				player.sendMessage(new TranslatableComponent("lt.reset"), uuid);
				return InteractionResult.SUCCESS;
			}
		}
		if (block instanceof BlockRedstoneAcceptor) {
			final CompoundTag comp = new CompoundTag();
			if (readBlockPosFromNBT(stack.getTag()) != null)
				return InteractionResult.FAIL;
			writeBlockPosToNBT(pos, comp);
			stack.setTag(comp);
			player.sendMessage(new TranslatableComponent("lt.setpos", pos.getX(), pos.getY(), pos.getZ()), uuid);
			player.sendMessage(new TranslatableComponent("lt.setpos.msg"), uuid);
			return InteractionResult.SUCCESS;
		}
		if (block instanceof BlockRedstoneEmitter) {
			final TileRedstoneEmitter emitter = (TileRedstoneEmitter) LevelIn.getBlockEntity(pos);
			final CompoundTag comp = stack.getTag();
			final BlockPos linkpos = Linkingtool.readBlockPosFromNBT(comp);
			if (emitter.link(linkpos)) {
				player.sendMessage(new TranslatableComponent("lt.linkedpos", linkpos.getX(), linkpos.getY(), linkpos.getZ()), uuid);
				stack.setTag(null);
				player.sendMessage(new TranslatableComponent("lt.reset"), uuid);
				return InteractionResult.SUCCESS;
			}
			player.sendMessage(new TranslatableComponent("lt.notlinked"), uuid);
			player.sendMessage(new TranslatableComponent("lt.notlinked.msg"), uuid);
			return InteractionResult.FAIL;
		}
		return InteractionResult.FAIL;
	}
	
	@Override
	public void appendHoverText(final ItemStack stack, @Nullable final Level LevelIn, final List<Component> tooltip, final TooltipFlag flagIn) {
		final CompoundTag nbt = stack.getTag();
		if (nbt != null) {
			final BlockPos pos = Linkingtool.readBlockPosFromNBT(nbt);
			if (pos != null) {
				tooltip.add(new TranslatableComponent("lt.linkedpos", pos.getX(), pos.getY(), pos.getZ()));
				return;
			}
		}
		tooltip.add(new TranslatableComponent("lt.notlinked"));
		tooltip.add(new TranslatableComponent("lt.notlinked.msg"));
	}
	
}
