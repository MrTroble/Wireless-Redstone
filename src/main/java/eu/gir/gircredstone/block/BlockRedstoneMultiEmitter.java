package eu.gir.gircredstone.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockRedstoneMultiEmitter extends BlockRedstoneEmitter {
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return super.newBlockEntity(pos, state);
    }
}
