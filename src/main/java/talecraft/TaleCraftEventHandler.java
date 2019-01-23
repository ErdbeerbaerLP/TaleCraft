package talecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandResultStats.Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import talecraft.blocks.world.WorkbenchBlock;
import talecraft.network.packets.GameruleSyncPacket;
import talecraft.network.packets.StringNBTCommandPacketClient;
import talecraft.server.ServerHandler;
import talecraft.util.UndoTask;
import talecraft.util.WorkbenchManager;
import talecraft.util.WorldFileDataHelper;

public class TaleCraftEventHandler {
	public TaleCraftEventHandler() {}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void keyEvent(KeyInputEvent event) {
		if(FMLCommonHandler.instance().getSide().isClient()) {
			if(TaleCraft.proxy instanceof talecraft.proxy.ClientProxy) {
				TaleCraft.proxy.asClient().getKeyboardHandler().on_key(event);
			}
		}
	}

	@SubscribeEvent
	public void tick(TickEvent event) {
		TaleCraft.proxy.tick(event);
	}

	@SubscribeEvent
	public void tickWorld(WorldTickEvent event) {
		TaleCraft.proxy.tickWorld(event);
	}

	@SubscribeEvent
	public void playerLoggedIn(PlayerLoggedInEvent event) {
		if(event.player instanceof EntityPlayerMP) {
			ServerHandler.getServerMirror(null).playerList().playerJoin((EntityPlayerMP) event.player);
			TaleCraft.network.sendTo(new StringNBTCommandPacketClient("client.network.join"), (EntityPlayerMP) event.player);
			TaleCraft.network.sendTo(new GameruleSyncPacket(event.player.getEntityWorld().getGameRules().writeToNBT()), (EntityPlayerMP) event.player);
		
		}
		
	}
	
	//CAN BE USED TO FIND CERTAIN EVENTS
//	@SubscribeEvent
//	public void event(Event event){
//      \/ FILTER SPAM EVENTS \/
//		if(event instanceof BiomeEvent) return;
//		if(event instanceof RenderGameOverlayEvent) return;
//		if(event instanceof GuiScreenEvent) return;
//		if(event instanceof EntityEvent.EnteringChunk) return;
//		if(event instanceof LivingEvent.LivingUpdateEvent) return;
//		if(event instanceof EntityViewRenderEvent) return;
//		if(event instanceof RenderLivingEvent) return;
//		if(event instanceof DrawBlockHighlightEvent) return;
//		if(event instanceof RenderWorldLastEvent) return;
//		if(event instanceof RenderHandEvent) return;
//		if(event instanceof TickEvent) return;
//		if(event instanceof InputEvent) return;
//		if(event instanceof MouseEvent) return;
//		if(event instanceof ChunkDataEvent) return;
//		if(event instanceof WorldEvent) return;
//		if(event instanceof FOVUpdateEvent) return;
//		if(event instanceof PlaySoundEvent) return;
//		if(event instanceof PlaySoundSourceEvent) return;
//		if(event instanceof BlockEvent) return;
//		if(event instanceof AttachCapabilitiesEvent) return;
//		if(event instanceof GuiOpenEvent) return;
//      /\ FILTER SPAM EVENTS /\
	
//		System.out.println(event.getClass().toString());
//	}

	
	@SubscribeEvent
	public void villagerInteract(EntityInteractSpecific e){
		/* XXX: Commented out because disabling features is NOT okay.
		if(e.getTarget() instanceof EntityVillager){
			if(e.getSide() == Side.CLIENT){
				e.getEntityPlayer().addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Villager trading is disabled in TaleCraft. Use the NPC instead."));
			}
			e.setCanceled(true);
		}
		//*/
	}

	@SubscribeEvent
	public void playerLoggedOut(PlayerLoggedOutEvent event) {
		if(event.player instanceof EntityPlayerMP) {
			ServerHandler.getServerMirror(null).playerList().playerLeave((EntityPlayerMP) event.player);
		}
	}
	
	@SubscribeEvent
	public void worldLoad(WorldEvent.Load event) {
		TaleCraft.worldsManager.registerWorld(event.getWorld());
		if(event.getWorld().isRemote)return;
		WorkbenchBlock.recipes = WorkbenchManager.fromNBT(WorldFileDataHelper.getTagFromFile(event.getWorld(), "workbench"));
		UndoTask.loadFromNBT(WorldFileDataHelper.getTagFromFile(event.getWorld(), "undo"));
		NBTTagCompound c = WorldFileDataHelper.getTagFromFile(event.getWorld(), "info");
		c.setString("version", Reference.MOD_VERSION);

		WorldFileDataHelper.saveNBTToWorld(event.getWorld(), "info", c);
	}

	@SubscribeEvent
	public void worldUnload(WorldEvent.Unload event) {
		TaleCraft.worldsManager.unregisterWorld(event.getWorld());
		if(event.getWorld().isRemote)return;
		WorldFileDataHelper.saveNBTToWorld(event.getWorld(), "workbench", WorkbenchBlock.recipes.toNBT());
		WorldFileDataHelper.saveNBTToWorld(event.getWorld(), "undo", UndoTask.toNBT());

		NBTTagCompound c = WorldFileDataHelper.getTagFromFile(event.getWorld(), "info");
		try {
			if(!c.hasKey("allowedUUIDs")) c.setString("allowedUUIDs", Minecraft.getMinecraft().getSession().getPlayerID()+";");
		System.out.println("ok");
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Errored");
		}

		WorldFileDataHelper.saveNBTToWorld(event.getWorld(), "info", c);
	}

	/*
	@SubscribeEvent
	public void guiRenderPre(GuiScreenEvent.DrawScreenEvent.Pre event) {
		event.getGui().drawString(event.getGui().mc.fontRendererObj, "Hello, World!", 1, 1, 0xFFFFFF);
	}
	 */

	@SubscribeEvent
	public void entityJoinWorld(EntityJoinWorldEvent event) {
		if(!event.getWorld().isRemote) {
			ServerHandler.handleEntityJoin(event.getWorld(), event.getEntity());
			TaleCraft.worldsManager.fetchManager(event.getWorld()).joinWorld(event.getEntity());
		}
	}

	@SubscribeEvent
	public void onLivingAttacked(LivingAttackEvent event) {
		World world = event.getEntity().getEntityWorld();

		if(world.isRemote) return;

		if(world.getGameRules().getBoolean("disable.damage.*")) {
			event.setCanceled(true);
			return;
		}

		if(event.getSource() == DamageSource.FALL && world.getGameRules().getBoolean("disable.damage.fall")) {
			event.setCanceled(true);
			return;
		}

		if(event.getSource() == DamageSource.DROWN && world.getGameRules().getBoolean("disable.damage.drown")) {
			event.setCanceled(true);
			return;
		}

		if(event.getSource() == DamageSource.LAVA && world.getGameRules().getBoolean("disable.damage.lava")) {
			event.setCanceled(true);
			return;
		}

		if(event.getSource() == DamageSource.MAGIC && world.getGameRules().getBoolean("disable.damage.magic")) {
			event.setCanceled(true);
			return;
		}

		if(event.getSource() == DamageSource.IN_FIRE && world.getGameRules().getBoolean("disable.damage.fire")) {
			event.setCanceled(true);
			return;
		}

		if(event.getSource() == DamageSource.IN_WALL && world.getGameRules().getBoolean("disable.damage.suffocate")) {
			event.setCanceled(true);
			return;
		}

	}

	@SubscribeEvent
	public void playerUseItem(final PlayerInteractEvent.RightClickItem event) {
		if(event.getSide() == Side.CLIENT) return;
		ItemStack stack = event.getItemStack();
		final EntityPlayer player = event.getEntityPlayer();
		boolean hasCommandTag = stack.hasTagCompound() ? stack.getTagCompound().hasKey("command") : false;
		if(hasCommandTag){
			String command = stack.getTagCompound().getString("command");
			FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(new ICommandSender() {
				@Override
				public void setCommandStat(Type type, int amount) {
					player.setCommandStat(type, amount);
				}
				@Override
				public boolean sendCommandFeedback() {
					return false;
				}
				@Override
				public MinecraftServer getServer() {
					return FMLCommonHandler.instance().getMinecraftServerInstance();
				}
				@Override
				public Vec3d getPositionVector() {
					return player.getPositionVector();
				}
				
				@Override
				public BlockPos getPosition() {
					return player.getPosition();
				}
				
				@Override
				public String getName() {
					return player.getName();
				}
				
				@Override
				public World getEntityWorld() {
					return player.getEntityWorld();
				}
				
				@Override
				public ITextComponent getDisplayName() {
					return player.getDisplayName();
				}
				@Override
				public Entity getCommandSenderEntity() {
					return player;
				}
				@Override
				public boolean canUseCommand(int permLevel, String commandName) {
					return true;
				}
				@Override
				public void sendMessage(ITextComponent component) {
					event.getEntityPlayer().sendMessage(component);
				}
			}, command);
		}
	}
	

}
