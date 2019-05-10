package talecraft.client.gui.misc;

import net.minecraft.client.gui.GuiScreen;
import talecraft.TaleCraft;

public class GuiCopyingWorld extends GuiScreen {
	private String text;
	public GuiCopyingWorld(String text) {
		this.text = text;
		System.out.println("INITIALIZING GUI!!!!!");
	}
	@Override
	public boolean doesGuiPauseGame() {
		// TODO Auto-generated method stub
		return false;
	}
	boolean drawn = false;
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		if(!drawn)System.out.println("DRAWING SCREEEEEEN");
		drawn=true;
		drawDefaultBackground();
		drawCenteredString(fontRenderer, "Moving world directory...", width/2, height/2, 16777215);
		drawCenteredString(fontRenderer, this.text, width/2, height/2+20, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
