package talecraft.blocks.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import talecraft.TaleCraft;
import talecraft.blocks.TCBlockContainer;
import talecraft.blocks.TCITriggerableBlock;
import talecraft.client.gui.blocks.GuiDelayBlock;
import talecraft.invoke.EnumTriggerState;
import talecraft.tileentity.DelayBlockTileEntity;

import java.util.Random;

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
            ((DelayBlockTileEntity) tileentity).trigger(triggerState);
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote)
            return;

        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof DelayBlockTileEntity) {
            ((DelayBlockTileEntity) tileentity).invokeFromUpdateTick();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote)
            return true;
        if (!TaleCraft.proxy.isBuildMode())
            return false;
        if (playerIn.isSneaking())
            return true;

        Minecraft mc = Minecraft.getMinecraft();
        mc.displayGuiScreen(new GuiDelayBlock((DelayBlockTileEntity) worldIn.getTileEntity(pos)));

        return true;
    }

}
