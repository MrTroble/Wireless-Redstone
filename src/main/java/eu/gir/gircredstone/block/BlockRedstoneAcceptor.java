package eu.gir.gircredstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockRedstoneAcceptor extends BlockBasic {
	
	public static final BooleanProperty POWER = BooleanProperty.create("power");

	public BlockRedstoneAcceptor() {
		super("acceptor");
		this.setDefaultState(getDefaultState().with(POWER, false));
	}
		
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockReader blockAccess, BlockPos pos, EnumFacing side) {
        return this.getStrongPower(blockState, blockAccess, pos, side);
	}

    @Override
    public int getStrongPower(IBlockState blockState, IBlockReader blockAccess, BlockPos pos, EnumFacing side) {
        return blockState.get(POWER) ? 15:0;
    }

    @Override
    protected void fillStateContainer(Builder<Block, IBlockState> builder) {
    	builder.add(POWER);
    }
    
}
