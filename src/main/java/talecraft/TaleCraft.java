package talecraft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.CompoundDataFixer;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import talecraft.client.gui.replaced_guis.GuiReplacer;
import talecraft.managers.TCWorldsManager;
import talecraft.network.TaleCraftNetwork;
import talecraft.proxy.ClientProxy;
import talecraft.proxy.CommonProxy;
import talecraft.script.GlobalScriptManager;
import talecraft.server.ServerHandler;
import talecraft.util.ConfigurationManager;
import talecraft.util.GuiHandler;
import talecraft.util.TCDataFixer;
import talecraft.util.TimedExecutor;
import talecraft.versionchecker.SendMessage;

import java.util.Random;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class TaleCraft {
    @Mod.Instance(Reference.MOD_ID)
    public static TaleCraft instance;
    public static ModContainer container;
    public static TCWorldsManager worldsManager;
    public static TaleCraftEventHandler eventHandler;
    public static GlobalScriptManager globalScriptManager;
    public static SimpleNetworkWrapper network;
    public static TimedExecutor timedExecutor;
    public static Logger logger;
    public static Random random;
    public static Gson gson;
    public static World lastVisitedWorld = null;
    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY, modId = Reference.MOD_ID)
    public static CommonProxy proxy;

    @SideOnly(Side.CLIENT)
    public static ClientProxy asClient() {
        return proxy.asClient();
    }

    public static NBTTagCompound getSettings(EntityPlayer player) {
        return proxy.getSettings(player);
    }

    //	public TaleCraft() {
//		// TODO Auto-generated constructor stub
//		System.out.println(System.getProperty("java.class.path"));
//		System.exit(0);
//	}
    @Mod.EventHandler
    public void modConstructing(FMLConstructionEvent event) {
        if (Loader.isModLoaded("discordrpc")) {
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        logger = event.getModLog();
        container = FMLCommonHandler.instance().findContainerFor(instance);
        logger.info("TaleCraft initialization...");
        logger.info("TaleCraft Version: " + Reference.MOD_VERSION);
        logger.info("TaleCraft ModContainer: " + container);
        logger.info("Creating/Reading configuration file!");
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        ConfigurationManager.init(config);
        config.save();
        logger.info("Configuration loaded!");
        MinecraftForge.EVENT_BUS.register(this);
        TaleCraftNetwork.preInit();
        random = new Random(42);

        worldsManager = new TCWorldsManager(this);
        timedExecutor = new TimedExecutor();
        globalScriptManager = new GlobalScriptManager();
        globalScriptManager.init(this, proxy);

        gson = new GsonBuilder().setPrettyPrinting().create();

        // Print debug information
        logger.info("TaleCraft CoreManager @" + worldsManager.hashCode());
        logger.info("TaleCraft TimedExecutor @" + timedExecutor.hashCode());
        logger.info("TaleCraft NET SimpleNetworkWrapper @" + network.hashCode());

        // Create and register the event handler
        eventHandler = new TaleCraftEventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
        MinecraftForge.EVENT_BUS.register(new SendMessage());

        if (ConfigurationManager.USE_VERSION_CHECKER && event.getSide() == Side.CLIENT)
            MinecraftForge.EVENT_BUS.register(new GuiReplacer());
        logger.info("TaleCraft Event Handler @" + eventHandler.hashCode());
        // Initialize all the Tabs/Blocks/Items/Commands etc.
        logger.info("Loading Tabs, Blocks, Items, Entities and Commands");
        TaleCraftTabs.init();
        TaleCraftEntities.init();
        TaleCraftCommands.init();

        // Initialize the Proxy
        logger.info("Initializing Proxy...");
        proxy.taleCraft = this;
        proxy.preInit(event);

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(TaleCraft.instance, new GuiHandler());
        // Convert tile entity IDs
        CompoundDataFixer compoundDataFixer = FMLCommonHandler.instance().getDataFixer();
        ModFixs dataFixer = compoundDataFixer.init(Reference.MOD_ID, 1);
        dataFixer.registerFix(FixTypes.BLOCK_ENTITY, new TCDataFixer());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        logger.info("TaleCraft initialized, all systems ready.");
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        MinecraftServer server = event.getServer();

        ICommandManager cmdmng = server.getCommandManager();
        if (cmdmng instanceof ServerCommandManager && cmdmng instanceof CommandHandler) {
            CommandHandler cmdhnd = (CommandHandler) cmdmng;
            TaleCraftCommands.register(cmdhnd);
        }

        // By calling this method, we create the ServerMirror for the given server.
        ServerHandler.getServerMirror(server);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        // Calling this method destroys all server instances that exist,
        // because the 'event' given above does NOT give us the server-instance that is being stopped.
        ServerHandler.destroyServerMirror(null);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        logger.info("Server started: " + event + " [TCINFO]");
        TaleCraftGameRules.registerGameRules(FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0).getGameRules());
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        //logger.info("Server stopped: " + event + " [TCINFO]");
    }

}
