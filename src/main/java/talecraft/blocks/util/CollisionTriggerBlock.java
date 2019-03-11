package talecraft.blocks.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import talecraft.TaleCraft;
import talecraft.blocks.TCInvisibleBlock;
import talecraft.blocks.tileentity.CollisionTriggerBlockTileEntity;
import talecraft.client.gui.blocks.GuiCollisionTriggerBlock;

public class CollisionTriggerBlock extends TCInvisibleBlock {

	public CollisionTriggerBlock() {
		// TODO Auto-generated constructor stub
		setRegistryName(TaleCraft.MOD_ID, "collisiontriggerblock");
	}
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new CollisionTriggerBlockTileEntity();
	}
	@Override
	public void onEntityCollision(IBlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if(entityIn instanceof EntityPlayerMP) {
			EntityPlayerMP p = (EntityPlayerMP) entityIn;
			if(p.isCreative())return;
		}else return;
		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if(tileEntity != null && tileEntity instanceof CollisionTriggerBlockTileEntity) {
			((CollisionTriggerBlockTileEntity)tileEntity).collision(entityIn);
		}
	}
@Override
@OnlyIn(Dist.CLIENT)
public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand,
		EnumFacing side, float hitX, float hitY, float hitZ) {
	if(!worldIn.isRemote)
		return true;
	if(!TaleCraft.isBuildMode())
		return false;
	if(playerIn.isSneaking())
		return true;

	Minecraft mc = Minecraft.getInstance();
	mc.displayGuiScreen(new GuiCollisionTriggerBlock((CollisionTriggerBlockTileEntity)worldIn.getTileEntity(pos)));

	return true;
	}


}
