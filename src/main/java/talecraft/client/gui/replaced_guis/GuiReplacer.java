package talecraft.client.gui.replaced_guis;

import java.lang.reflect.Field;

import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiCustomizeSkin;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiSnooper;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.gui.ForgeGuiFactory.ForgeConfigGui;
import net.minecraftforge.fml.client.GuiModList;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import talecraft.TaleCraft;
import talecraft.client.gui.misc.CrashQuestion;
import talecraft.client.gui.replaced_guis.map.MapCreator;
import talecraft.client.gui.replaced_guis.map.MapSelector;

public class GuiReplacer { //It Rhymes! (No anymore, sry)

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e){
		if(e.getGui() != null) {
			if(e.getGui().getClass() == GuiIngameMenu.class) {
				e.setGui(new NewIngameMenu());
			}
			if(e.getGui().getClass() == GuiCreateWorld.class) {
				try {
					GuiScreen parentScreen = ObfuscationReflectionHelper.getPrivateValue(GuiCreateWorld.class, (GuiCreateWorld)e.getGui(), "field_146332_f","parentScreen");
					if(parentScreen instanceof MapCreator) {
						((MapCreator)parentScreen).chunkProviderSettingsJson = ((GuiCreateWorld)e.getGui()).chunkProviderSettingsJson;
						e.setGui(parentScreen);
					}
					else e.setGui(new MapCreator());
				} catch (SecurityException | IllegalArgumentException e1) {
					e1.printStackTrace();
					e.setGui(new MapCreator());
				}
			}
			if(e.getGui().getClass() == GuiMainMenu.class) {
				e.setGui(new CustomMainMenu());
			}
			if(e.getGui().getClass() == GuiGameOver.class) {
				try {
					e.setGui(new NewGameOverScreen(ObfuscationReflectionHelper.getPrivateValue(GuiGameOver.class,(GuiGameOver) e.getGui(), "field_184871_f","causeOfDeathIn")));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(e.getGui().getClass() == GuiMultiplayer.class) {
				System.err.println("Redirecting to somehow opened multiplayer menu to main menu");
				e.setGui(new CustomMainMenu());
			}
		}
		
//		System.out.println(e.getGui());
	}
	

}
