package eu.gir.gircredstone.tile;

import eu.gir.gircredstone.item.Linkingtool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileRedstoneEmitter extends TileEntity {

	private BlockPos linkedpos;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		Linkingtool.writeBlockPosToNBT(linkedpos, compound);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.linkedpos = Linkingtool.readBlockPosFromNBT(compound);
	}
	
	public void link(final BlockPos pos) {
		this.linkedpos = pos; 
	}
	
}
