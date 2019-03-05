package talecraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import talecraft.TaleCraftRegistered;
import talecraft.blocks.tileentity.TileEntityBarrier;

public class TCInvisibleBlock extends BlockContainer{

	public TCInvisibleBlock() {
		super(Block.Properties.create(TaleCraftRegistered.MATERIAL_ADMINIUM).sound(SoundType.STONE).hardnessAndResistance(-1f, 6000001.0F));
	}
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean isFullCube(IBlockState state) {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean propagatesSkylightDown(IBlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}


	public boolean isSolid(IBlockState state) {
		return false;
	}

	/**
	 * @deprecated call via {@link IBlockState#getAmbientOcclusionLightValue()} whenever possible.
	 * Implementing/overriding is fine.
	 */
	@OnlyIn(Dist.CLIENT)
	public float getAmbientOcclusionLightValue(IBlockState state) {
		return 1.0F;
	}

	public void dropBlockAsItemWithChance(IBlockState state, World worldIn, BlockPos pos, float chancePerItem, int fortune) {
	}
@Override
public boolean hasTileEntity() {
	// TODO Auto-generated method stub
	return true;
}
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int tickRate(IWorldReaderBase worldIn) {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public void onPlayerDestroy(IWorld world, BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		if(world.getTileEntity(pos) != null) world.getTileEntity(pos).remove();
	}

}
