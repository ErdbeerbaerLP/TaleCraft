package talecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import talecraft.TaleCraftRegistered;

public abstract class TCBlock extends BlockContainer{

	public TCBlock() {
		super(Block.Properties.create(TaleCraftRegistered.MATERIAL_ADMINIUM).sound(SoundType.STONE).hardnessAndResistance(-1f, 6000001.0F));
	}
	public TCBlock(Block.Properties prop) {
		super(prop.sound(SoundType.STONE).hardnessAndResistance(-1f, 6000001.0F));
	}
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		// TODO Auto-generated method stub
	}

	

	public void dropBlockAsItemWithChance(IBlockState state, World worldIn, BlockPos pos, float chancePerItem, int fortune) {
	}

	
	@Override
	public void onPlayerDestroy(IWorld world, BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		if(world.getTileEntity(pos) != null) world.getTileEntity(pos).remove();
	}


}
