package talecraft.blocks.util;

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
import talecraft.blocks.tileentity.MemoryBlockTileEntity;
import talecraft.client.gui.blocks.GuiMemoryBlock;
import talecraft.invoke.EnumTriggerState;

public class MemoryBlock extends TCInvisibleBlock implements TCITriggerableBlock {

	public MemoryBlock() {
		// TODO Auto-generated constructor stub
		setRegistryName(TaleCraft.MOD_ID, "memoryblock");
	}
	@Override
	public TileEntity createNewTileEntity(IBlockReader r) {
		return new MemoryBlockTileEntity();
	}

	@Override
	public void trigger(World world, BlockPos position, EnumTriggerState triggerState) {
		if (world.isRemote)
			return;

		TileEntity tileentity = world.getTileEntity(position);

		if (tileentity instanceof MemoryBlockTileEntity) {
			((MemoryBlockTileEntity)tileentity).trigger(triggerState);
		}
	}
	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			return true;
		if(!TaleCraft.isBuildMode())
			return false;
		if(playerIn.isSneaking())
			return true;

		Minecraft mc = Minecraft.getInstance();
		mc.displayGuiScreen(new GuiMemoryBlock((MemoryBlockTileEntity)worldIn.getTileEntity(pos)));

		return true;
	}

}
