package talecraft.client.gui.replaced_guis;

import java.lang.reflect.Field;

import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import talecraft.client.gui.misc.CrashQuestion;
import talecraft.client.gui.replaced_guis.map.MapCreator;
import talecraft.client.gui.replaced_guis.map.MapSelector;

public class GuiReplacer { //It Rhymes! (No anymore, sry)

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e){
		if(e.getGui() != null && e.getGui().getClass() == GuiIngameMenu.class) {
			e.setGui(new NewIngameMenu());
		}
		if(e.getGui() != null && e.getGui().getClass() == GuiCreateWorld.class) {
			  try {
				  Field parentScreenField = ((GuiCreateWorld)e.getGui()).getClass().getDeclaredField("parentScreen");    
				  parentScreenField.setAccessible(true);
				  GuiScreen parentScreen = (GuiScreen) parentScreenField.get(((GuiCreateWorld)e.getGui()));

				  if(parentScreen instanceof MapCreator) {
					  ((MapCreator)parentScreen).chunkProviderSettingsJson = ((GuiCreateWorld)e.getGui()).chunkProviderSettingsJson;
					  e.setGui(parentScreen);
				  }
				  else e.setGui(new MapCreator());
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
				e1.printStackTrace();
				e.setGui(new MapCreator());
			}
		}
		if(e.getGui() != null && e.getGui().getClass() == GuiMainMenu.class) {
			e.setGui(new CustomMainMenu());
		}
//		System.out.println(e.getGui()); 
	}
	
}
