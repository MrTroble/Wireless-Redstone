package eu.gir.gircredstone.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockRedstoneAcceptor extends BlockBasic {
	
	public static final PropertyBool POWER = PropertyBool.create("power");

	public BlockRedstoneAcceptor() {
		super("acceptor");
		this.setDefaultState(getDefaultState().withProperty(POWER, false));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWER) ? 0:1;
	}
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return getStrongPower(blockState, blockAccess, pos, side);
    }

    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return blockState.getValue(POWER) ? 15:0;
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWER, meta == 1);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { POWER });
	}

}
