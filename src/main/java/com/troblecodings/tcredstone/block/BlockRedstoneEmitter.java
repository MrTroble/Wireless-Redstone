package com.troblecodings.tcredstone.block;

import com.troblecodings.tcredstone.init.TCRedstoneInit;
import com.troblecodings.tcredstone.tile.TileRedstoneEmitter;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BlockRedstoneEmitter extends Block implements ITileEntityProvider {

    public BlockRedstoneEmitter(final Properties properties) {
        super(properties);
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
        if (entity instanceof TileRedstoneEmitter) {
            final TileRedstoneEmitter emitter = (TileRedstoneEmitter) entity;
            final BlockPos linkedpos = emitter.getLinkedPos();
            if (linkedpos == null) {
                player.sendMessage(new TextComponentTranslation("em.notlinked"));
            } else {
                if (player.isSneaking()) {
                    emitter.unlink();
                    player.sendMessage(new TextComponentTranslation("em.unlink", linkedpos.getX(),
                            linkedpos.getY(), linkedpos.getZ()));
                } else {
                    player.sendMessage(new TextComponentTranslation("lt.linkedpos",
                            linkedpos.getX(), linkedpos.getY(), linkedpos.getZ()));
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
