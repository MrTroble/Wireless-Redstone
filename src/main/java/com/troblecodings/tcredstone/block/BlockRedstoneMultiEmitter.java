package com.troblecodings.tcredstone.block;

import java.util.List;

import com.troblecodings.tcredstone.init.TCRedstoneInit;
import com.troblecodings.tcredstone.tile.TileRedstoneMultiEmitter;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockRedstoneMultiEmitter extends BlockRedstoneEmitter {

    public BlockRedstoneMultiEmitter(final Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createNewTileEntity(final IBlockReader pos) {
        return new TileRedstoneMultiEmitter();
    }

    @Override
    public boolean onBlockActivated(final IBlockState state, final World world, final BlockPos pos,
            final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float hitX,
            final float hitY, final float hitZ) {
        if (world.isRemote)
            return true;
        if (player.getHeldItem(hand).getItem().equals(TCRedstoneInit.RS_LINKER))
            return false;
        final TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileRedstoneMultiEmitter) {
            final TileRedstoneMultiEmitter emitter = (TileRedstoneMultiEmitter) entity;
            final List<BlockPos> listOfPositions = emitter.getLinkedPos();
            if (listOfPositions == null) {
                player.sendMessage(new TextComponentTranslation("em.notlinked"));
            } else {
                if (player.isSneaking()) {
                    emitter.unlink();
                    listOfPositions.forEach(
                            blockpos -> player.sendMessage(new TextComponentTranslation("em.unlink",
                                    blockpos.getX(), blockpos.getY(), blockpos.getZ())));
                } else {
                    listOfPositions.forEach(blockpos -> player
                            .sendMessage(new TextComponentTranslation("lt.linkedpos",
                                    blockpos.getX(), blockpos.getY(), blockpos.getZ())));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void neighborChanged(final IBlockState state, final World world, final BlockPos pos,
            final Block blockIn, final BlockPos fromPos) {
        if (world.isRemote)
            return;
        final TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileRedstoneMultiEmitter) {
            final TileRedstoneMultiEmitter emitter = (TileRedstoneMultiEmitter) entity;
            emitter.redstoneUpdate(world.isBlockPowered(pos));
        }
    }

}
