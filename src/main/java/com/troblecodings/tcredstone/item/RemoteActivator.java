package com.troblecodings.tcredstone.item;

import java.util.function.BiPredicate;

import com.troblecodings.linkableapi.Linkingtool;
import com.troblecodings.tcredstone.tile.TileRedstoneEmitter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RemoteActivator extends Linkingtool {

    public RemoteActivator(final ItemGroup tab, final BiPredicate<World, BlockPos> predicate) {
        super(tab, predicate, _u -> true);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World level, final EntityPlayer player,
            final EnumHand hand) {
        final ItemStack itemstack = player.getHeldItem(hand);
        if (!hand.equals(EnumHand.MAIN_HAND) || level.isRemote)
            return ActionResult.newResult(EnumActionResult.PASS, itemstack);
        final NBTTagCompound comp = itemstack.getTag();
        final BlockPos linkpos = NBTUtil.readBlockPos(comp);
        final boolean state = TileRedstoneEmitter.redstoneUpdate(linkpos, level);
        message(player, "ra.state" + String.valueOf(state));
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
    }

}
