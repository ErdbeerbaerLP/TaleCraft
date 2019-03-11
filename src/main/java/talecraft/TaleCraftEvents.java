package talecraft;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiCustomizeSkin;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiSnooper;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.GuiModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import talecraft.blocks.UnderwaterBarrier;
import talecraft.blocks.tileentity.TileEntityBarrier;
import talecraft.blocks.util.CollisionTriggerBlock;
import talecraft.blocks.util.LightBlock;
import talecraft.client.gui.replaced_guis.CustomMainMenu;
import talecraft.items.WandItem;
import talecraft.blocks.tileentity.CollisionTriggerBlockTileEntity;
import talecraft.blocks.tileentity.LightBlockTE;
@EventBusSubscriber(bus=Bus.MOD)
public class TaleCraftEvents {
	
	// REGISTRIES
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> ev) {
		TaleCraft.logger.info("Registering Blocks");
		final IForgeRegistry<Block> reg = ev.getRegistry();
		
		reg.register(new UnderwaterBarrier());
		reg.register(new LightBlock());
		reg.register(new CollisionTriggerBlock());
	}
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> ev) {
		TaleCraft.logger.info("Registering Items");
		final IForgeRegistry<Item> reg = ev.getRegistry();
		
		reg.register(new ItemBlock(TaleCraftRegistered.WATER_BARRIER, new Item.Properties()).setRegistryName(TaleCraft.MOD_ID, "barrier"));
		reg.register(new ItemBlock(TaleCraftRegistered.LIGHT_BLOCK, new Item.Properties()).setRegistryName(TaleCraft.MOD_ID, "lightblock"));
		reg.register(new WandItem().setRegistryName(TaleCraft.MOD_ID, "wand"));
		reg.register(new ItemBlock(TaleCraftRegistered.COLLISION_TRIGGER, new Item.Properties()).setRegistryName(TaleCraft.MOD_ID, "collisiontriggerblock"));
	}
	@SubscribeEvent
	public static void registerEntityType(RegistryEvent.Register<EntityType<?>> ev) {
		TaleCraft.logger.info("Registering Entities");
	}
	@SubscribeEvent
	public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> ev) {
		TaleCraft.logger.info("Registering TileEntities");
		TaleCraftRegistered.TE_BARRIER = TileEntityType.register(TaleCraft.MOD_ID+":te_barrier", TileEntityType.Builder.create(TileEntityBarrier::new)); 
		TaleCraftRegistered.TE_LIGHT_BLOCK = TileEntityType.register(TaleCraft.MOD_ID+":te_light", TileEntityType.Builder.create(LightBlockTE::new)); 
		TaleCraftRegistered.TE_COLLISION_TRIGGER = TileEntityType.register(TaleCraft.MOD_ID+":te_collisiontrigger", TileEntityType.Builder.create(CollisionTriggerBlockTileEntity::new)); 
		}
	
	
	
	
	@SubscribeEvent
	public static void onGuiChanged(GuiOpenEvent e){
		if(e.getGui() != null) {
//			if(e.getGui().getClass() == GuiIngameMenu.class) {
//				e.setGui(new NewIngameMenu());
//			}
//			if(e.getGui().getClass() == GuiCreateWorld.class) {
//				try {
//					GuiScreen parentScreen = ObfuscationReflectionHelper.getPrivateValue(GuiCreateWorld.class, (GuiCreateWorld)e.getGui(), "field_146332_f","parentScreen");
//					if(parentScreen instanceof MapCreator) {
//						((MapCreator)parentScreen).chunkProviderSettingsJson = ((GuiCreateWorld)e.getGui()).chunkProviderSettingsJson;
//						e.setGui(parentScreen);
//					}
//					else e.setGui(new MapCreator());
//				} catch (SecurityException | IllegalArgumentException e1) {
//					e1.printStackTrace();
//					e.setGui(new MapCreator());
//				}
//			}
			if(e.getGui().getClass() == GuiMainMenu.class) {
				e.setGui(new CustomMainMenu());
			}
//			if(e.getGui().getClass() == GuiGameOver.class) {
//				try {
//					e.setGui(new NewGameOverScreen(ObfuscationReflectionHelper.getPrivateValue(GuiGameOver.class,(GuiGameOver) e.getGui(), "field_184871_f","causeOfDeathIn")));
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
			if(e.getGui().getClass() == GuiMultiplayer.class) {
				System.err.println("Redirecting to somehow opened multiplayer menu to main menu");
				e.setGui(new CustomMainMenu());
			}
			if(e.getGui() instanceof GuiOptions || e.getGui() instanceof GuiVideoSettings || e.getGui() instanceof GuiSnooper|| e.getGui() instanceof ScreenChatOptions || e.getGui() instanceof GuiControls || e.getGui() instanceof GuiScreenOptionsSounds || e.getGui() instanceof GuiCustomizeSkin || e.getGui() instanceof GuiScreenResourcePacks|| e.getGui() instanceof GuiLanguage)
				TaleCraft.setPresence("Editing Options", "talecraft");
			
			if(e.getGui() instanceof GuiModList)
				TaleCraft.setPresence("Looking at the Mod list", "talecraft");
		}
		
//		System.out.println(e.getGui());
	}
	
}
