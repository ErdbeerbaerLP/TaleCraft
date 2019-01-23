package talecraft.client.gui.replaced_guis.map;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import talecraft.client.gui.replaced_guis.CustomMainMenu;

public class GuiNoEditPermission extends GuiScreen {
	@Override
	public void initGui() {
		// TODO Auto-generated method stub
		addButton(new GuiButtonExt(0, width/2, height/2+30, "Back"));
	}
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		// TODO Auto-generated method stub
		switch(button.id) {
		case 0:
			mc.displayGuiScreen(new MapSelector(new CustomMainMenu()));
			break;
		}
	}
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		// TODO Auto-generated method stub
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		drawDefaultBackground();
		this.buttonList.get(0).x = width/2-100;
		this.buttonList.get(0).y = height/2+30;
		drawCenteredString(fontRenderer, "You are not allowed to edit this Map!", width/2, height/2-20, 16777215);
		drawCenteredString(fontRenderer, "Contact the Map Author to get edit permission", width/2, height/2, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
