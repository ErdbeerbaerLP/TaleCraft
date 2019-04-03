package talecraft.blocks.util;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import talecraft.TaleCraft;
import talecraft.blocks.TCITriggerableBlock;
import talecraft.blocks.TCInvisibleBlock;
import talecraft.blocks.tileentity.DelayBlockTileEntity;
import talecraft.client.gui.blocks.GuiDelayBlock;
import talecraft.invoke.EnumTriggerState;

public class DelayBlock extends TCInvisibleBlock implements TCITriggerableBlock {
	public DelayBlock() {
		setRegistryName(TaleCraft.MOD_ID, "delayblock");
	}
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		// TODO Auto-generated method stub
		return new DelayBlockTileEntity();
	}

	@Override
	public void trigger(World world, BlockPos position, EnumTriggerState triggerState) {
		if (world.isRemote)
			return;

		TileEntity tileentity = world.getTileEntity(position);

		if (tileentity instanceof DelayBlockTileEntity) {
			((DelayBlockTileEntity)tileentity).trigger(triggerState);
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
		mc.displayGuiScreen(new GuiDelayBlock((DelayBlockTileEntity)worldIn.getTileEntity(pos)));

		return true;
	}
	

}
