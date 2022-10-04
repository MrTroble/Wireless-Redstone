package eu.gir.gircredstone.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BlockRedstoneAcceptor extends Block {

    public static final BooleanProperty POWER = BooleanProperty.create("power");

    public BlockRedstoneAcceptor(final Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(POWER, false));
    }

    @Override
    public boolean isSignalSource(final BlockState blockState) {
        return true;
    }

    @Override
    public int getSignal(final BlockState blockState, final BlockGetter world, final BlockPos pos,
            final Direction direction) {
        return this.getDirectSignal(blockState, world, pos, direction);
    }

    @Override
    public int getDirectSignal(final BlockState blockState, final BlockGetter world,
            final BlockPos pos, final Direction direction) {
        return blockState.getValue(POWER) ? 15 : 0;
    }

    @Override
    protected void createBlockStateDefinition(final Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }

}
