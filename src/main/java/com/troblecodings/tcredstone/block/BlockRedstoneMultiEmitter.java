package com.troblecodings.tcredstone.block;

import java.util.List;

import com.troblecodings.tcredstone.init.TCRedstoneInit;
import com.troblecodings.tcredstone.tile.TileRedstoneMultiEmitter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockRedstoneMultiEmitter extends BlockRedstoneEmitter {

    public BlockRedstoneMultiEmitter(final Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity newBlockEntity(final IBlockReader pos) {
        return new TileRedstoneMultiEmitter();
    }

    @Override
    public ActionResultType use(final BlockState state, final World world, final BlockPos pos,
            final PlayerEntity player, final Hand hand, final BlockRayTraceResult hit) {
        if (world.isClientSide)
            return ActionResultType.PASS;
        if (player.getItemInHand(hand).getItem().equals(TCRedstoneInit.RS_LINKER.get()))
            return ActionResultType.PASS;
        final TileEntity entity = world.getBlockEntity(pos);
        if (entity instanceof TileRedstoneMultiEmitter) {
            final TileRedstoneMultiEmitter emitter = (TileRedstoneMultiEmitter) entity;
            final List<BlockPos> listOfPositions = emitter.getLinkedPos();
            if (listOfPositions == null) {
                player.sendMessage(new TranslationTextComponent("em.notlinked"));
            } else {
                if (player.isCrouching()) {
                    emitter.unlink();
                    listOfPositions.forEach(
                            blockpos -> player.sendMessage(new TranslationTextComponent("em.unlink",
                                    blockpos.getX(), blockpos.getY(), blockpos.getZ())));
                } else {
                    listOfPositions.forEach(blockpos -> player
                            .sendMessage(new TranslationTextComponent("lt.linkedpos",
                                    blockpos.getX(), blockpos.getY(), blockpos.getZ())));
                }
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    @Override
    public void neighborChanged(final BlockState state, final World world, final BlockPos pos,
            final Block blockIn, final BlockPos fromPos, final boolean isMoving) {
        if (world.isClientSide)
            return;
        final TileEntity entity = world.getBlockEntity(pos);
        if (entity instanceof TileRedstoneMultiEmitter) {
            final TileRedstoneMultiEmitter emitter = (TileRedstoneMultiEmitter) entity;
            emitter.redstoneUpdate(world.hasNeighborSignal(pos));
        }
    }

}
