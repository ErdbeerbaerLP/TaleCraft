package talecraft.client.gui.replaced_guis;

import java.lang.reflect.Field;

import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiReplacer { //It Rhymes! (No anymore, sry)

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e){
		if(e.getGui() != null && e.getGui().getClass() == GuiWorldSelection.class){
			e.setGui(new NewWorldSelector(null));
		}
		if(e.getGui() != null && e.getGui().getClass() == GuiCreateWorld.class) {
			  try {
				  Field parentScreenField = ((GuiCreateWorld)e.getGui()).getClass().getDeclaredField("parentScreen");    
				  parentScreenField.setAccessible(true);
				  GuiScreen parentScreen = (GuiScreen) parentScreenField.get(((GuiCreateWorld)e.getGui()));

				  if(parentScreen instanceof NewWorldCreator) {
					  ((NewWorldCreator)parentScreen).chunkProviderSettingsJson = ((GuiCreateWorld)e.getGui()).chunkProviderSettingsJson;
					  e.setGui(parentScreen);
				  }
				  else e.setGui(new NewWorldCreator());
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
				e1.printStackTrace();
				e.setGui(new NewWorldCreator());
			}
		}
//		System.out.println(e.getGui()); 
	}
	
}
