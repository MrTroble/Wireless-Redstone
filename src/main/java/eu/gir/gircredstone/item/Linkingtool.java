package eu.gir.gircredstone.item;

import java.util.List;

import javax.annotation.Nullable;

import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.block.BlockRedstoneEmitter;
import eu.gir.gircredstone.tile.TileRedstoneEmitter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class Linkingtool extends Item {

    private final boolean canLink;

    public Linkingtool(final Properties properties, final boolean canLink) {
        super(properties);
        this.canLink = canLink;
    }

    public Linkingtool(final Properties properties) {
        this(properties, true);
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
        if (compound != null && compound.contains(ID_X) && compound.contains(ID_Y)
                && compound.contains(ID_Z)) {
            return new BlockPos(compound.getInt(ID_X), compound.getInt(ID_Y),
                    compound.getInt(ID_Z));
        }
        return null;
    }

    @Override
    public InteractionResult onItemUseFirst(final ItemStack stack, final UseOnContext ctx) {
        final Level levelIn = ctx.getLevel();
        final Player player = ctx.getPlayer();
        final BlockPos pos = ctx.getClickedPos();
        if (levelIn.isClientSide)
            return InteractionResult.PASS;
        final Block block = levelIn.getBlockState(pos).getBlock();
        if (player.isCrouching()) {
            if (Linkingtool.readBlockPosFromNBT(stack.getTag()) != null) {
                stack.setTag(null);
                message(player, "lt.reset");
                return InteractionResult.SUCCESS;
            }
        }
        if (block instanceof BlockRedstoneAcceptor) {
            final CompoundTag comp = new CompoundTag();
            if (readBlockPosFromNBT(stack.getTag()) != null)
                return InteractionResult.FAIL;
            writeBlockPosToNBT(pos, comp);
            stack.setTag(comp);
            message(player, "lt.setpos", pos.getX(), pos.getY(), pos.getZ());
            message(player, "lt.setpos.msg");
            return InteractionResult.SUCCESS;
        }
        if ((block instanceof BlockRedstoneEmitter) && this.canLink) {
            final TileRedstoneEmitter emitter = (TileRedstoneEmitter) levelIn.getBlockEntity(pos);
            final CompoundTag comp = stack.getTag();
            final BlockPos linkpos = Linkingtool.readBlockPosFromNBT(comp);
            if (emitter.link(linkpos)) {
                message(player, "lt.linkedpos", linkpos.getX(), linkpos.getY(), linkpos.getZ());
                stack.setTag(null);
                message(player, "lt.reset");
                return InteractionResult.SUCCESS;
            }
            message(player, "lt.notlinked");
            message(player, "lt.notlinked.msg");
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(final ItemStack stack, @Nullable final Level levelIn,
            final List<Component> tooltip, final TooltipFlag flagIn) {
        final CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            final BlockPos pos = Linkingtool.readBlockPosFromNBT(nbt);
            if (pos != null) {
                tooltip(tooltip, "lt.linkedpos", pos.getX(), pos.getY(), pos.getZ());
                return;
            }
        }
        tooltip(tooltip, "lt.notlinked");
        tooltip(tooltip, "lt.notlinked.msg");
    }

    @SuppressWarnings({
            "rawtypes", "unchecked"
    })
    public void tooltip(final List list, final String text, final Object... obj) {
        list.add(getComponent(text, obj));
    }

    public void message(final Player player, final String text, final Object... obj) {
        player.sendSystemMessage(getComponent(text, obj));
    }

    public MutableComponent getComponent(final String text, final Object... obj) {
        return MutableComponent.create(new TranslatableContents(text, obj));
    }
}
