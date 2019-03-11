package talecraft.client.gui.components;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import talecraft.client.gui.GuiColors;

public class GuiLabel extends net.minecraft.client.gui.GuiLabel {

	public GuiLabel(FontRenderer font, List<String> text, int x, int y, int color) {
		super(text, color, font);
		this.x = x;
		this.y = y;
		// TODO Auto-generated constructor stub
		
	}
	public GuiLabel(FontRenderer font, String text, int x, int y, int color) {
		this(font, new ArrayList<String>() {{add(text);}}, x, y, color);
	}
	public GuiLabel(FontRenderer fontRenderer, String string, int x, int y) {
		this(fontRenderer, string, x, y, GuiColors.WHITE);
	}
	public GuiLabel(FontRenderer font, List<String> text, int x, int y) {
		this(font, text, x, y, GuiColors.WHITE);
	}
	

}
