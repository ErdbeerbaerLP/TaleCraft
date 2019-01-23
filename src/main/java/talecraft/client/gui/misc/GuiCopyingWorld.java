package talecraft.client.gui.misc;

import net.minecraft.client.gui.GuiScreen;

public class GuiCopyingWorld extends GuiScreen {
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Moving world directory...", width/2, height/2, 16777215);
	}
}
