package talecraft;

import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod(TaleCraft.MOD_ID)
public class TaleCraft {
	public static final String MOD_ID = "talecraft";
	public static final String VERSION = "1.0.0";
	public static final Logger logger = Logger.getLogger(TaleCraft.MOD_ID);
	public static World lastVisitedWorld;
	public static Minecraft mc = Minecraft.getInstance();
//	public static GlobalScriptManager globalScriptManager;
	public TaleCraft() {
		TaleCraftRegistered.load();
		//Register loading state listeners
		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
		MinecraftForge.EVENT_BUS.addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.addListener(this::clientSetup);
		MinecraftForge.EVENT_BUS.addListener(this::serverSetup);
		MinecraftForge.EVENT_BUS.addListener(this::loadComplete);
		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);

		//Register event handlers
//		MinecraftForge.EVENT_BUS.register(TaleCraftEvents.class);
	}
	public void serverStarting(FMLServerStartingEvent evt)
	{
		//Register commands...
	}
	public void commonSetup(FMLCommonSetupEvent event) {
	}
	public void clientSetup(FMLClientSetupEvent event) {

	}
	public void serverSetup(FMLDedicatedServerSetupEvent event) {

	}
	public void postInit(InterModProcessEvent event) {

	}
	public void loadComplete(FMLLoadCompleteEvent event) {

	}
	/**
	 * @return TRUE, if the client is in build-mode (aka: creative-mode), FALSE if not.
	 **/
	public static boolean isBuildMode() {
		return mc.playerController != null && mc.playerController.isInCreativeMode();
	}
//	public static ClientProxy asClient() {
//		return proxy.asClient();
//	}
//
//	public static NBTTagCompound getSettings(EntityPlayer player) {
//		return proxy.getSettings(player);
//	}
//	
	public static void setPresence(String title, String iconKey) {
		setPresence(title, "", iconKey);
	}
	public static void setPresence(String title, String subtitle, String iconKey) {
		//Unused for now
	}


}
