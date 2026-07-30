package com.troblecodings.tcredstone.item;

import java.util.Optional;
import java.util.function.BiPredicate;

import com.troblecodings.linkableapi.Linkingtool;
import com.troblecodings.tcredstone.TCRedstoneMain;
import com.troblecodings.tcredstone.tile.TileRedstoneEmitter;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RemoteActivator extends Linkingtool {

    public RemoteActivator(final Settings settings, final BiPredicate<World, BlockPos> predicate) {
        super(settings, null, predicate, _u1 -> false, TCRedstoneMain.COMPOUND_DATA);
    }

    @Override
    public ActionResult use(final World level, final PlayerEntity player, final Hand hand) {
        final ItemStack itemstack = player.getStackInHand(hand);
        final NbtCompound tag = itemstack.get(TCRedstoneMain.COMPOUND_DATA);
        if (tag != null) {
            if (!hand.equals(Hand.MAIN_HAND) || level.isClient())
                return ActionResult.PASS;
            final NbtCompound comp = getOrCreateNbt(itemstack);
            if (comp.contains(LINKINGTOOL_TAG)) {
                final Optional<BlockPos> linkpos = readBlockPos(comp, LINKINGTOOL_TAG);
                final boolean state = TileRedstoneEmitter.redstoneUpdate(linkpos.get(), level);
                message(player, "ra.state", String.valueOf(state));
            }
        }
        return ActionResult.SUCCESS;
    }
}
