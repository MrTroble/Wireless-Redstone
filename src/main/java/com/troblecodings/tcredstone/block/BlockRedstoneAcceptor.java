package com.troblecodings.tcredstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockRedstoneAcceptor extends Block {

    public static final BooleanProperty POWER = BooleanProperty.create("power");

    public BlockRedstoneAcceptor(final Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(POWER, false));
    }

    @Override
    public boolean canProvidePower(final IBlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(final IBlockState blockState, final IBlockReader blockAccess,
            final BlockPos pos, final EnumFacing side) {
        return this.getStrongPower(blockState, blockAccess, pos, side);
    }

    @Override
    public int getStrongPower(final IBlockState blockState, final IBlockReader blockAccess,
            final BlockPos pos, final EnumFacing side) {
        return blockState.get(POWER) ? 15 : 0;
    }

    @Override
    protected void fillStateContainer(final Builder<Block, IBlockState> builder) {
        builder.add(POWER);
    }

}
