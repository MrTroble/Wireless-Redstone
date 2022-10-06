package eu.gir.gircredstone.tile;

import eu.gir.gircredstone.init.GIRCInit;
import eu.gir.linkableapi.ILinkableTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileRedstoneMultiEmitter extends BlockEntity implements ILinkableTile {

    public TileRedstoneMultiEmitter(final BlockPos pos, final BlockState state) {
        super(GIRCInit.MULTI_EMITER_TILE.get(), pos, state);
    }

    @Override
    public boolean hasLink() {
        return false;
    }

    @Override
    public boolean link(final BlockPos pos) {
        return false;
    }

    @Override
    public boolean unlink() {
        return false;
    }
}
