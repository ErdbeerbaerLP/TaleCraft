package talecraft.blocks.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import talecraft.TaleCraft;
import talecraft.blocks.TCITriggerableBlock;
import talecraft.blocks.TCInvisibleBlock;
import talecraft.blocks.tileentity.LightBlockTE;
import talecraft.client.gui.blocks.GuiLightBlock;
import talecraft.invoke.EnumTriggerState;

public class LightBlock extends TCInvisibleBlock implements TCITriggerableBlock {
	public LightBlock() {
		// TODO Auto-generated constructor stub
		setRegistryName(TaleCraft.MOD_ID, "lightblock");
	}
	@Override
	public boolean isSolid(IBlockState state) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		// TODO Auto-generated method stub
		return new LightBlockTE();
	}

	@Override
	public int getLightValue(IBlockState state, IWorldReader world, BlockPos pos) {
		TileEntity tileEntity = world.getTileEntity(pos);

		if(tileEntity != null && tileEntity instanceof LightBlockTE) {
			return ((LightBlockTE) tileEntity).getLightValue();
		}

		return 15;
	}

	@Override
	public void trigger(World world, BlockPos position, EnumTriggerState triggerState) {
		System.out.println("WORLD: "+(world.isRemote? "REMOTE": "CLIENT"));
		if (world.isRemote)
			return;
		
		TileEntity tileEntity = world.getTileEntity(position);
		System.out.println(tileEntity);
		System.out.println(triggerState.name());
		if(tileEntity != null && tileEntity instanceof LightBlockTE) {
			switch (triggerState) {
			case ON: ((LightBlockTE) tileEntity).setLightActive(true); break;
			case OFF: ((LightBlockTE) tileEntity).setLightActive(false); break;
			case INVERT: ((LightBlockTE) tileEntity).toggleLightActive(); break;
			case IGNORE: ((LightBlockTE) tileEntity).setLightActive(true); break;
			default: break;
			}
		}
	}
	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			return true;
		if(!TaleCraft.isBuildMode())
			return false;
		if(player.isSneaking())
			return true;

		Minecraft mc = Minecraft.getInstance();
		mc.displayGuiScreen(new GuiLightBlock((LightBlockTE)worldIn.getTileEntity(pos)));

		return true;
	}

	@Override
	public void onPlayerDestroy(IWorld world, BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		super.onPlayerDestroy(world, pos, state);
		world.getLight(pos);
	}


}
