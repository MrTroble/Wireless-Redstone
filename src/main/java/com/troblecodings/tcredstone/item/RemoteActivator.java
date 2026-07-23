package com.troblecodings.tcredstone.item;

import java.util.Optional;
import java.util.function.BiPredicate;

import com.troblecodings.linkableapi.Linkingtool;
import com.troblecodings.tcredstone.TCRedstoneMain;
import com.troblecodings.tcredstone.tile.TileRedstoneEmitter;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RemoteActivator extends Linkingtool {

    public RemoteActivator(final Properties properties, final CreativeModeTab tab,
            final BiPredicate<Level, BlockPos> predicate) {
        super(properties, tab, predicate, _u -> false, TCRedstoneMain.COMPOUND_DATA);
    }

    @Override
    public InteractionResult use(final Level level, final Player player,
            final InteractionHand hand) {
        final ItemStack itemstack = player.getItemInHand(hand);
        final CompoundTag tag = itemstack.get(TCRedstoneMain.COMPOUND_DATA);
        if (tag != null) {
            if (!hand.equals(InteractionHand.MAIN_HAND) || level.isClientSide())
                return InteractionResult.PASS;
            final CompoundTag comp = getOrCreateNbt(itemstack);
            if (comp.contains(LINKINGTOOL_TAG)) {
                final Optional<BlockPos> linkpos = readBlockPos(comp, LINKINGTOOL_TAG);
                final boolean state = TileRedstoneEmitter.redstoneUpdate(linkpos.get(), level);
                message(player, "ra.state", String.valueOf(state));
            }
        }
        return InteractionResult.SUCCESS;
    }
}
