package eu.gir.gircredstone.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockRedstoneMultiEmitter extends BlockRedstoneEmitter {

    public BlockRedstoneMultiEmitter(final Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(final BlockPos pos, final BlockState state) {
        return super.newBlockEntity(pos, state);
    }
}
