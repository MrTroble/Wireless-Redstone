package com.troblecodings.tcredstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class BlockRedstoneAcceptor extends Block {

	public static final BooleanProperty POWER = BooleanProperty.of("power");

	public BlockRedstoneAcceptor(final Settings properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(POWER, false));
	}

	@Override
	public boolean emitsRedstonePower(final BlockState blockState) {
		return true;
	}

	@Override
	public int getStrongRedstonePower(final BlockState blockState, final BlockView world, final BlockPos pos,
			final Direction direction) {
		return this.getWeakRedstonePower(blockState, world, pos, direction);
	}

	@Override
	public int getWeakRedstonePower(final BlockState blockState, final BlockView world, final BlockPos pos,
			final Direction direction) {
		return blockState.get(POWER) ? 15 : 0;
	}

	@Override
	protected void appendProperties(final Builder<Block, BlockState> builder) {
		builder.add(POWER);
	}

}
