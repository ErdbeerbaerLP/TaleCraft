package talecraft.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import talecraft.TaleCraft;
import talecraft.blocks.tileentity.TileEntityBarrier;
public class UnderwaterBarrier extends TCWaterloggableInvisBlock{
	public UnderwaterBarrier() {
		setRegistryName(TaleCraft.MOD_ID, "barrier");
		// TODO Auto-generated constructor stub
	}
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		// TODO Auto-generated method stub
		return new TileEntityBarrier();
	}
	@Override
	public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
		// TODO Auto-generated method stub
		return createNewTileEntity(world);
	}
}

