package talecraft.blocks.util;

import java.util.UUID;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import talecraft.TaleCraft;
import talecraft.TaleCraftRegistered;
import talecraft.blocks.TCITriggerableBlock;
import talecraft.blocks.TCInvisibleBlock;
import talecraft.blocks.tileentity.SummonBlockTileEntity;
import talecraft.blocks.tileentity.SummonBlockTileEntity.SummonOption;
import talecraft.invoke.EnumTriggerState;

// summonblock
public class SummonBlock extends TCInvisibleBlock implements TCITriggerableBlock {

	public SummonBlock() {
		// TODO Auto-generated constructor stub
		setRegistryName(TaleCraft.MOD_ID, "summonblock");
	}
	@Override
	public TileEntity createNewTileEntity(IBlockReader r) {
		return new SummonBlockTileEntity();
	}

	@Override
	public void trigger(World world, BlockPos position, EnumTriggerState triggerState) {
		if (world.isRemote)
			return;
		if(!triggerState.equals(EnumTriggerState.ON)) return;
		TileEntity tileentity = world.getTileEntity(position);

		if (tileentity instanceof SummonBlockTileEntity) {
			((SummonBlockTileEntity)tileentity).trigger(triggerState);
		}
	}
	@Override
	public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer playerIn, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = playerIn.getHeldItem(hand);
		if(heldItem != null && heldItem.getItem() == TaleCraftRegistered.ITEM_ENTITCLONE && !world.isRemote){
			if(heldItem.hasTag()){
				if(heldItem.getTag().hasKey("entity_data")){
					SummonBlockTileEntity te = (SummonBlockTileEntity) world.getTileEntity(pos);
					NBTTagCompound entitydat = heldItem.getTag().getCompound("entity_data");
					entitydat.setUniqueId("UUID", UUID.randomUUID());
					SummonOption[] oldArray = te.getSummonOptions();
					SummonOption[] newArray = new SummonOption[oldArray.length+1];
					System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
					SummonOption option = new SummonOption();
					option.setWeight(1F);
					NBTTagCompound tag = new NBTTagCompound();
					tag.merge(entitydat);
					option.setData(tag);
					newArray[oldArray.length] = option;
					te.setSummonOptions(newArray);
					te.markDirty();
					if(!world.isRemote)playerIn.sendMessage(new TextComponentString("Entity data has been added to summon block!"));
					return true;
				}else{
					if(!world.isRemote)playerIn.sendMessage(new TextComponentString("No entity has been selected!"));
					return true;
				}
			}
		}
		if(!world.isRemote){
			return true;
		}
		return openGui(world, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}
	
	@OnlyIn(Dist.CLIENT)
	private boolean openGui(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if(!TaleCraft.isBuildMode())
			return false;
		if(playerIn.isSneaking())
			return true;
		if(heldItem == null || heldItem.getItem() != TaleCraftRegistered.ITEM_ENTITCLONE){
			Minecraft mc = Minecraft.getInstance();
			// TODO recreate GUI    mc.displayGuiScreen(new GuiSummonBlock((SummonBlockTileEntity)world.getTileEntity(pos)));
			playerIn.sendMessage(new TextComponentString(TextFormatting.RED+"This GUI is not finished!"));
		}
		return true;
	}

}
