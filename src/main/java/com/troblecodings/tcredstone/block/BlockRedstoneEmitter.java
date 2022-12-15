package com.troblecodings.tcredstone.block;

import com.troblecodings.tcredstone.init.TCRedstoneInit;
import com.troblecodings.tcredstone.tile.TileRedstoneEmitter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BlockRedstoneEmitter extends Block implements ITileEntityProvider {

    public BlockRedstoneEmitter(final Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(final BlockState state, final World world, final BlockPos pos,
            final PlayerEntity player, final Hand hand, final BlockRayTraceResult hit) {
        if (!world.isRemote)
            return false;
        if (player.getHeldItem(hand).getItem().equals(TCRedstoneInit.RS_LINKER.get()))
            return false;
        final TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileRedstoneEmitter) {
            final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
            final BlockPos linkedpos = emitter.getLinkedPos();
            if (linkedpos == null) {
                player.sendMessage(new TranslationTextComponent("em.notlinked"));
            } else {
                if (player.isSneaking()) {
                    emitter.unlink();
                    player.sendMessage(new TranslationTextComponent("em.unlink", linkedpos.getX(),
                            linkedpos.getY(), linkedpos.getZ()));
                } else {
                    player.sendMessage(new TranslationTextComponent("lt.linkedpos",
                            linkedpos.getX(), linkedpos.getY(), linkedpos.getZ()));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void neighborChanged(final BlockState state, final World world, final BlockPos pos,
            final Block blockIn, final BlockPos fromPos, final boolean isMoving) {
        if (!world.isRemote)
            return;
        final TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileRedstoneEmitter) {
            final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
            emitter.redstoneUpdate(world.isBlockPowered(pos));
        }
    }

    @Override
    public TileEntity createNewTileEntity(final IBlockReader worldIn) {
        return new TileRedstoneEmitter();
    }

}
