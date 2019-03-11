package talecraft.client.gui.components;

import net.minecraftforge.fml.client.config.GuiButtonExt;

public class GuiTCButton extends GuiButtonExt{
	
	public GuiTCButton(int xPos, int yPos, String displayString) {
		this(-1, xPos, yPos, displayString);
	}
	
	public GuiTCButton(int id, int xPos, int yPos, String displayString) {
		super(id, xPos, yPos, displayString);
	}
	public GuiTCButton(int id, int xPos, int yPos, int width, int height, String displayString) {
		super(id, xPos, yPos, width, height, displayString);
	}
//	public void setID(int id){
//		this.id = id;
//	}
	@Override
	public void onClick(double mouseX, double mouseY) {
		// TODO Auto-generated method stub
		super.onClick(mouseX, mouseY);
		this.onClick();
	}
	public void onClick() {}
	public void setTooltip(String tooltip) {
		// TODO Auto-generated method stub

	}
	@Override
	protected int getHoverState(boolean mouseOver) {
		// TODO Auto-generated method stub
		if(mouseOver && enabled) {
			//Draw tooltip
		}
		return super.getHoverState(mouseOver);
		
	}

}
