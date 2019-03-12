package talecraft.items;

import java.util.Arrays;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import talecraft.TaleCraft;
import talecraft.network.packets.PlayerNBTDataMergePacket;

public class WandItem extends TCItem implements TCITriggerableItem{

	@Override
	public EnumActionResult onItemUse(ItemUseContext ctx) {
		World world = ctx.getWorld();
		BlockPos pos = ctx.getPos();
		EntityPlayer player = ctx.getPlayer();
		if(world.isRemote)
			return EnumActionResult.PASS;
		
		applyWand(player, pos);
		
		return EnumActionResult.PASS;
	}
	@Override
	public boolean canPlayerBreakBlockWhileHolding(IBlockState state, World worldIn, BlockPos pos,
			EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override //Clears the wand selection
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(world.isRemote)
			return ActionResult.newResult(EnumActionResult.PASS, stack);
		
		{ // do far-away raytrace...
			float lerp = 1f;
			float dist = 256;
			
			Vec3d start = getPositionEyes(lerp, player);
			Vec3d direction = player.getLook(lerp);
			Vec3d end = start.add(direction.x * dist, direction.y * dist, direction.z * dist);

			RayTraceResult result = world.rayTraceBlocks(start, end);
			
			if(result.getBlockPos() != null) {
				applyWand(player, result.getBlockPos());
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			}
		}
		
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

	@Override
	// Warning: Forge Method
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		// Check if we are on the server-side.
		if(!player.world.isRemote) {
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			String cmd = "/tc_editentity " + entity.getUniqueID().toString();
			try {
				ServerLifecycleHooks.getCurrentServer().getCommandManager().getDispatcher().execute(cmd, playerMP.getCommandSource());
			} catch (CommandSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// by returning TRUE, we prevent damaging the entity being hit.
		return true;
	}
	
	public static final void applyWand(EntityPlayer player, BlockPos pos) {
		NBTTagCompound compound = player.getEntityData();
		NBTTagCompound tcWand = null;

		if(!compound.hasKey("tcWand")) {
			tcWand = new NBTTagCompound();
			compound.setTag("tcWand", tcWand);
		} else {
			tcWand = compound.getCompound("tcWand");
		}
		
		{
			// Double Call Prevention Hack
			long timeNow = player.world.getWorldInfo().getGameTime();
			long timePre = tcWand.getLong("DCPH");
			
			if(timeNow == timePre) {
				return;
			} else {
				tcWand.setLong("DCPH", timeNow);
			}
		}

		int[] pos$$ = new int[]{pos.getX(),pos.getY(),pos.getZ()};
		
		tcWand.setIntArray("cursor", Arrays.copyOf(pos$$, 3));

		if(!tcWand.hasKey("boundsA") || !tcWand.getBoolean("enabled")) {
			tcWand.setIntArray("boundsA", pos$$);
			tcWand.setIntArray("boundsB", pos$$);
			tcWand.setBoolean("enabled", true);
		} else {
			boolean flip = tcWand.getBoolean("flip");
			if(flip) {
				tcWand.setIntArray("boundsB", pos$$);
			} else {
				tcWand.setIntArray("boundsA", pos$$);
			}
			tcWand.setBoolean("flip", !flip);
		}

		TaleCraft.network.sendTo(new PlayerNBTDataMergePacket(compound), ((EntityPlayerMP) player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
	}

	public static final int[] getBoundsFromPlayerOrNull(EntityPlayer player) {
		return getBoundsFromPlayerDataOrNull(player.getEntityData());
	}

	public static final int[] getBoundsFromPlayerDataOrNull(NBTTagCompound playerData) {
		if(playerData.hasKey("tcWand"))
			return getBoundsFromTcWandOrNull(playerData.getCompound("tcWand"));
		return null;
	}

	public static final int[] getBoundsFromTcWandOrNull(NBTTagCompound tcWand) {
		if(!tcWand.hasKey("boundsA") || !tcWand.hasKey("boundsB")) {
			return null;
		}

		int[] a = tcWand.getIntArray("boundsA");
		int[] b = tcWand.getIntArray("boundsB");

		int ix = Math.min(a[0], b[0]);
		int iy = Math.min(a[1], b[1]);
		int iz = Math.min(a[2], b[2]);
		int ax = Math.max(a[0], b[0]);
		int ay = Math.max(a[1], b[1]);
		int az = Math.max(a[2], b[2]);

		return new int[]{ix,iy,iz,ax,ay,az};
	}

	public static final void setBounds(EntityPlayer player, int ix, int iy, int iz, int ax, int ay, int az) {
		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound wandData = playerData.getCompound("tcWand");

		int _ix = Math.min(ix, ax);
		int _iy = Math.min(iy, ay);
		int _iz = Math.min(iz, az);
		int _ax = Math.max(ix, ax);
		int _ay = Math.max(iy, ay);
		int _az = Math.max(iz, az);

		if(_iy < 0) _iy = 0;
		if(_ay > 255) _ay = 255;

		wandData.setIntArray("boundsA", new int[]{_ix,_iy,_iz});
		wandData.setIntArray("boundsB", new int[]{_ax,_ay,_az});

		TaleCraft.network.sendTo(new PlayerNBTDataMergePacket(playerData), ((EntityPlayerMP) player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
	}

	public static long getBoundsVolume(int[] bounds) {
		int x = Math.abs(bounds[3] - bounds[0]);
		int y = Math.abs(bounds[4] - bounds[1]);
		int z = Math.abs(bounds[5] - bounds[2]);
		return x*y*z;
	}
	
	@Override
	public void trigger(World world, EntityPlayerMP player, ItemStack stack) {
		NBTTagCompound compound = player.getEntityData();
		compound.getCompound("tcWand").setBoolean("enabled", false);
		compound.getCompound("tcWand").setIntArray("boundsA", new int[]{0, 0, 0});
		compound.getCompound("tcWand").setIntArray("boundsB", new int[]{0, 0, 0});
		player.sendMessage(new TextComponentString("Wand selection has been cleared!"));
		TaleCraft.network.sendTo(new PlayerNBTDataMergePacket(compound), player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
	}

}
