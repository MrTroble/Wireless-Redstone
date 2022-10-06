package eu.gir.gircredstone.tile;

import com.mojang.authlib.minecraft.TelemetrySession;
import eu.gir.gircredstone.init.GIRCInit;
import eu.gir.linkableapi.ILinkableTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class TileRedstoneMultiEmitter extends BlockEntity implements ILinkableTile {

    private List<BlockPos> listOfPositions = new ArrayList<>();

    public TileRedstoneMultiEmitter(final BlockPos pos, final BlockState state) {
        super(GIRCInit.MULTI_EMITER_TILE.get(), pos, state);
    }

    @Override
    public boolean hasLink() {
        return false;
    }

    @Override
    public boolean link(final BlockPos pos) {
        if (pos != null && !listOfPositions.contains(pos)) {
            listOfPositions.add(pos);
            return true;
        }
        return false;
    }

    @Override
    public boolean unlink() {
        return false;
    }
}
