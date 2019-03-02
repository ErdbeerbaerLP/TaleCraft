package talecraft;

import java.util.logging.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod(TaleCraft.MODID)
public class TaleCraft {
	public static final String MODID = "talecraft";
	public static final String VERSION = "1.0.0";
	public static final Logger logger = Logger.getLogger(TaleCraft.MODID);
	public TaleCraft() {
		//Register loading state listeners
		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
		MinecraftForge.EVENT_BUS.addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.addListener(this::clientSetup);
		MinecraftForge.EVENT_BUS.addListener(this::serverSetup);
		MinecraftForge.EVENT_BUS.addListener(this::loadComplete);
		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);

		//Register event handlers
		MinecraftForge.EVENT_BUS.register(TaleCraftEvents.class);
	}
	public void serverStarting(FMLServerStartingEvent evt)
	{

	}
	public void commonSetup(FMLCommonSetupEvent event) {

	}
	public void clientSetup(FMLClientSetupEvent event) {

	}
	public void serverSetup(FMLDedicatedServerSetupEvent event) {

	}
	public void loadComplete(FMLLoadCompleteEvent event) {

	}
	public void postInit(InterModProcessEvent event) {

	}


}
