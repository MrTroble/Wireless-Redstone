package eu.gir.gircredstone.tile;

import eu.gir.gircredstone.block.BlockRedstoneAcceptor;
import eu.gir.gircredstone.init.GIRCInit;
import eu.gir.gircredstone.item.Linkingtool;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileRedstoneEmitter extends BlockEntity {

    public TileRedstoneEmitter(final BlockPos pos, final BlockState state) {
        super(GIRCInit.EMITER_TILE.get(), pos, state);
    }

    private BlockPos linkedpos = null;

    @Override
    public void load(final CompoundTag compound) {
        super.load(compound);
        this.linkedpos = Linkingtool.readBlockPosFromNBT(compound);
    }

    @Override
    protected void saveAdditional(final CompoundTag compound) {
        super.saveAdditional(compound);
        Linkingtool.writeBlockPosToNBT(linkedpos, compound);
    }

    public boolean link(final BlockPos pos) {
        if (pos == null)
            return false;
        this.linkedpos = pos;
        return true;
    }

    public boolean unlink() {
        if (this.linkedpos == null)
            return false;
        this.linkedpos = null;
        return true;
    }

    public BlockPos getLinkedPos() {
        return this.linkedpos;
    }

    public void redstoneUpdate(final boolean enabled) {
        redstoneUpdate(enabled, linkedpos, level);
    }

    public static boolean redstoneUpdate(final boolean enabled, final BlockPos linkedpos,
            final Level level) {
        if (linkedpos != null) {
            final BlockState state = level.getBlockState(linkedpos);
            if (state.getBlock() instanceof BlockRedstoneAcceptor) {
                level.setBlock(linkedpos, state.setValue(BlockRedstoneAcceptor.POWER, enabled), 3);
            }
        }
        return enabled;
    }

    public static boolean redstoneUpdate(final BlockPos linkedpos, final Level level) {
        if (linkedpos != null) {
            final BlockState state = level.getBlockState(linkedpos);
            if (state.getBlock() instanceof BlockRedstoneAcceptor) {
                final boolean newState = !state.getValue(BlockRedstoneAcceptor.POWER);
                level.setBlock(linkedpos, state.setValue(BlockRedstoneAcceptor.POWER, newState), 3);
                return newState;
            }
        }
        return false;
    }
}
