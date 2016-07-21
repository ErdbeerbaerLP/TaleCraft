package de.longor.talecraft.blocks.util;

import java.util.Random;

import de.longor.talecraft.TaleCraft;
import de.longor.talecraft.blocks.TCBlockContainer;
import de.longor.talecraft.blocks.TCITriggerableBlock;
import de.longor.talecraft.blocks.util.tileentity.DelayBlockTileEntity;
import de.longor.talecraft.client.gui.blocks.GuiDelayBlock;
import de.longor.talecraft.invoke.EnumTriggerState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DelayBlock extends TCBlockContainer implements TCITriggerableBlock {

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
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
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (worldIn.isRemote)
			return;

		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof DelayBlockTileEntity) {
			((DelayBlockTileEntity)tileentity).invokeFromUpdateTick();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			return true;
		if(!TaleCraft.proxy.isBuildMode())
			return false;
		if(playerIn.isSneaking())
			return true;

		Minecraft mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(new GuiDelayBlock((DelayBlockTileEntity)worldIn.getTileEntity(pos)));

		return true;
	}

}