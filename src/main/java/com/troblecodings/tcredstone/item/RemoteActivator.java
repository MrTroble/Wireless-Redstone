package com.troblecodings.tcredstone.item;

import com.troblecodings.linkableapi.Linkingtool;
import com.troblecodings.tcredstone.init.TCInit;
import com.troblecodings.tcredstone.tile.TileRedstoneEmitter;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RemoteActivator extends Linkingtool {

	public RemoteActivator(ItemGroup tab) {
		super(tab, TCInit::acceptAcceptor, _u1 -> false);
	}

	@Override
	public TypedActionResult<ItemStack> use(final World level, final PlayerEntity player, final Hand hand) {
		final ItemStack itemstack = player.getStackInHand(hand);
		if (!hand.equals(Hand.MAIN_HAND) || level.isClient())
			return TypedActionResult.pass(itemstack);
		final NbtCompound comp = itemstack.getNbt();
		final BlockPos linkpos = NbtHelper.toBlockPos(comp);
		final boolean state = TileRedstoneEmitter.redstoneUpdate(linkpos, level);
		message(player, "ra.state", String.valueOf(state));
		return TypedActionResult.success(itemstack);
	}

}
