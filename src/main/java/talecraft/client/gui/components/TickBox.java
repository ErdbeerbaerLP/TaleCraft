package talecraft.client.gui.components;

import net.minecraftforge.fml.client.config.GuiCheckBox;

public class TickBox extends GuiCheckBox {
	private String[] tooltips = new String[0];
	public TickBox(int xPos, int yPos, String displayString, boolean isChecked) {
		super(-1, xPos, yPos, displayString, isChecked);
	}
	public TickBox(int xPos, int yPos, String displayString) {
		this(xPos, yPos, displayString, false);
	}
	public TickBox(int xPos, int yPos) {
		this(xPos, yPos, "", false);
	}
	public void setTooltip(String... strings) {
		this.tooltips = strings;
	}
	

}
