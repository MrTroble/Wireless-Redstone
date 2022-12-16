package com.troblecodings.tcredstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockRedstoneAcceptor extends Block {

    public static final PropertyBool POWER = PropertyBool.create("power");

    public BlockRedstoneAcceptor(final Material material) {
        super(material);
        this.setDefaultState(this.getDefaultState().withProperty(POWER, false));
    }

    @Override
    public boolean canProvidePower(final IBlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(final IBlockState blockState, final IBlockAccess blockAccess,
            final BlockPos pos, final EnumFacing side) {
        return this.getStrongPower(blockState, blockAccess, pos, side);
    }

    @Override
    public int getStrongPower(final IBlockState blockState, final IBlockAccess blockAccess,
            final BlockPos pos, final EnumFacing side) {
        return blockState.getValue(POWER) ? 15 : 0;
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return state.getValue(POWER) ? 0:1;
    }

    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(POWER, meta == 1);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {
                POWER
        });
    }

}
