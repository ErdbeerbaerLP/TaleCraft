package talecraft.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.IBlockReader;
import talecraft.TaleCraft;
import talecraft.blocks.tileentity.TileEntityBarrier;
public class UnderwaterBarrier extends TCInvisibleBlock{
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
	 @Override
	  public EnumBlockRenderType getRenderType(IBlockState iBlockState) {
	    return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	  }
	
}

