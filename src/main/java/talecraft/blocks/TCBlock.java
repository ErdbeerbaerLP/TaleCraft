package talecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import talecraft.TaleCraft;

public class TCBlock extends Block{

	public TCBlock() {
		super(Block.Properties.create(TaleCraft.MATERIAL_ADMINIUM).sound(SoundType.STONE).hardnessAndResistance(-1f, 6000001.0F));
	}
	@Override
	public boolean isFullCube(IBlockState state) {
		// TODO Auto-generated method stub
		return true;
	}

}
