package talecraft.blocks.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ITickable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import talecraft.TaleCraft;
import talecraft.TaleCraftRegistered;

public class TileEntityBarrier extends TileEntity{

	public TileEntityBarrier() {
		super(TaleCraftRegistered.TE_BARRIER);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean shouldRenderInPass(int pass) {
		// TODO Auto-generated method stub
		return TaleCraft.isBuildMode() ? (pass == 0) : false;
	}
	@Override
	@OnlyIn(Dist.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 2048; // 128 blocks!
	}
	 

}
