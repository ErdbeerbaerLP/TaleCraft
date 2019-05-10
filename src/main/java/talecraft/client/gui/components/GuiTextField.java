package talecraft.client.gui.components;

import net.minecraft.client.gui.FontRenderer;
import talecraft.TaleCraft;

public class GuiTextField extends net.minecraft.client.gui.GuiTextField {
	private String[] tooltip = new String[0];
	private boolean acceptsColors = false;
	public GuiTextField(FontRenderer fontRenderer, int x, int y, int width, int height) {
		super(-1, fontRenderer, x, y, width, height);
		setMaxStringLength(100);
	}
	@Override
	public void writeText(String textToWrite) {
		final String colorCodePlaceholder = "[JUSTARANDOMCOLORCODEPLACEHOLDER"+TaleCraft.random.nextInt(1000)+"]";
		if(acceptsColors) {
			textToWrite = textToWrite.replace("\00A7", colorCodePlaceholder);
			super.writeText(textToWrite);
			this.setText(getText().replace(colorCodePlaceholder, "\00A7"));
		}else {
			super.writeText(textToWrite);
		}
	}

	/**
	 * 	currently unused
	 */
	public void setTooltip(String... strings) {

	}
	public void setAcceptsColors(boolean acceptsColors) {
		this.acceptsColors = acceptsColors;
	}
	public void setWidth(int width) {
		// TODO Auto-generated method stub
		this.width = width;
	}
}
