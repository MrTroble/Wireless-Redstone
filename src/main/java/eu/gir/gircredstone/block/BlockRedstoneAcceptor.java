package eu.gir.gircredstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockRedstoneAcceptor extends BlockBasic {
	
	public static final BooleanProperty POWER = BooleanProperty.create("power");

	public BlockRedstoneAcceptor() {
		super("acceptor");
		this.setDefaultState(getDefaultState().with(POWER, false));
	}
		
	@Override
	public boolean canProvidePower(BlockState state) {
		return true;
	}
	
	@Override
	public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return this.getStrongPower(blockState, blockAccess, pos, side);
	}

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.get(POWER) ? 15:0;
    }

    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
    	builder.add(POWER);
    }
    
}
