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
	
	public BlockRedstoneAcceptor(final Properties p_49795_) {
		super(p_49795_);
		this.registerDefaultState(this.defaultBlockState().setValue(POWER, false));
	}
	
	@Override
	public boolean isSignalSource(final BlockState p_149744_1_) {
		return true;
	}
	
	@Override
	public int getSignal(final BlockState p_180656_1_, final BlockGetter p_180656_2_, final BlockPos p_180656_3_, final Direction p_180656_4_) {
		return this.getDirectSignal(p_180656_1_, p_180656_2_, p_180656_3_, p_180656_4_);
	}
	
	@Override
	public int getDirectSignal(final BlockState blockState, final BlockGetter p_176211_2_, final BlockPos p_176211_3_, final Direction p_176211_4_) {
		return blockState.getValue(POWER) ? 15 : 0;
	}
	
	@Override
	protected void createBlockStateDefinition(final Builder<Block, BlockState> builder) {
		builder.add(POWER);
	}
	
}
