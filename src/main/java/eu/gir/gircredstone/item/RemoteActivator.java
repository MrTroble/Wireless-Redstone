package eu.gir.gircredstone.item;

import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RemoteActivator extends Linkingtool {

    public RemoteActivator(final Properties properties) {
        super(properties, false);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(final Level level, final Player player,
            final InteractionHand hand) {
        final ItemStack itemstack = player.getItemInHand(hand);
        if (!hand.equals(InteractionHand.MAIN_HAND) || level.isClientSide)
            return InteractionResultHolder.pass(itemstack);
        final CompoundTag comp = itemstack.getTag();
        final BlockPos linkpos = Linkingtool.readBlockPosFromNBT(comp);
        final boolean state = TileRedstoneEmitter.redstoneUpdate(linkpos, level);
        message(player, "ra.state", String.valueOf(state));
        return InteractionResultHolder.success(itemstack);
    }

}
