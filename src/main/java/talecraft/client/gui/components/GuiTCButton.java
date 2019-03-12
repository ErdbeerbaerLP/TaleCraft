package talecraft.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;

public class GuiTCButton extends GuiButtonExt{
	private ButtonIcon BUTTON_ICON;
	private String[] tooltips = new String[0];
	public enum ButtonIcon{
		NONE(null),
		ADD(new ResourceLocation("talecraft:textures/gui/add.png")),
		DELETE(new ResourceLocation("talecraft:textures/gui/delete.png")),
		INVEDIT(new ResourceLocation("talecraft:textures/gui/invokeedit.png")),
		PLAY(new ResourceLocation("talecraft:textures/gui/play.png")),
		PAUSE(new ResourceLocation("talecraft:textures/gui/pause.png")),
		STOP(new ResourceLocation("talecraft:textures/gui/stop.png")),
		SAVE(new ResourceLocation("talecraft:textures/gui/save.png")),
		NEW(new ResourceLocation("talecraft:textures/gui/new.png")),
		FILE(new ResourceLocation("talecraft:textures/gui/file/editors/none.png")),
		FILE_TXT(new ResourceLocation("talecraft:textures/gui/file/editors/txt.png")),
		FILE_NBT(new ResourceLocation("talecraft:textures/gui/file/editors/nbt.png")),
		FILE_BIN(new ResourceLocation("talecraft:textures/gui/file/editors/bin.png"));
		
		private final ResourceLocation location;
		ButtonIcon(ResourceLocation loc) {
			this.location = loc;
		}
		public ResourceLocation getResourceLocation() {
			return location;
		}
	}
	
	public GuiTCButton(int xPos, int yPos, String displayString) {
		this(xPos, yPos, 100, displayString);
	}
	
	public GuiTCButton(int xPos, int yPos, int width, String displayString) {
		this(xPos, yPos, width, 20, displayString);
	}
	public GuiTCButton(int xPos, int yPos, int width, String displayString, ButtonIcon texture) {
		this(xPos, yPos, width, 20, displayString, texture);
	}
	public GuiTCButton(int xPos, int yPos, int width, int height, String displayString) {
		this(xPos, yPos, width, height, displayString, ButtonIcon.NONE);
	}
	public GuiTCButton(int xPos, int yPos, int width, int height, String displayString, ButtonIcon texture) {
		super(-1, xPos, yPos, width, height, displayString);
		this.BUTTON_ICON = texture;
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
	public GuiTCButton setTooltip(String... tooltips) {
		this.tooltips  = tooltips;
		return this;
	}
	/**
     * Draws this button to the screen.
     */
    @Override
    public void render(int mouseX, int mouseY, float partial)
    {
        if (this.visible)
        {
            Minecraft mc = Minecraft.getInstance();
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int k = this.getHoverState(this.hovered);
            GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.x, this.y, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
            this.renderBg(mc, mouseX, mouseY);
            int color = 14737632;

            if (packedFGColor != 0)
            {
                color = packedFGColor;
            }
            else if (!this.enabled)
            {
                color = 10526880;
            }
            else if (this.hovered)
            {
                color = 16777120;
            }
            int bx = this.x;
            if(BUTTON_ICON != ButtonIcon.NONE) {
            	Minecraft.getInstance().getTextureManager().bindTexture(BUTTON_ICON.getResourceLocation());
				this.drawModalRectWithCustomSizedTexture(bx + 2, y + 2, 0, 0, 16, 16, 16, 16);

				// ! MODIFY X !
				bx += 2 + 16;
			}
            String buttonText = this.displayString;
            int strWidth = mc.fontRenderer.getStringWidth(buttonText);
            int ellipsisWidth = mc.fontRenderer.getStringWidth("...");

            if (strWidth > width - 6 && strWidth > ellipsisWidth)
                buttonText = mc.fontRenderer.trimStringToWidth(buttonText, width - 6 - ellipsisWidth).trim() + "...";

            this.drawCenteredString(mc.fontRenderer, buttonText, bx + this.width / 2, this.y + (this.height - 8) / 2, color);
            
            
            if(this.hovered && this.tooltips.length != 0) {
            	//Draw hovering text
            }
        }
        
    }
}
