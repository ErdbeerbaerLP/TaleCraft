package talecraft.client.gui.replaced_guis;

import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class SelectorBase extends GuiScreen {
	/** The screen to return to when this closes (always Main Menu). */
	protected GuiScreen prevScreen;

	protected GuiTextField field_212352_g;
	protected SelectionListBase selectionList;

	public SelectorBase(GuiScreen screenIn) {
		this.prevScreen = screenIn;
	}

	public boolean mouseScrolled(double p_mouseScrolled_1_) {
		return this.selectionList.mouseScrolled(p_mouseScrolled_1_);
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void tick() {
		this.field_212352_g.tick();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	protected void initGui() {
		this.mc.keyboardListener.enableRepeatEvents(true);
		this.children.add(this.selectionList);
		this.field_212352_g.setFocused(true);
		this.field_212352_g.setCanLoseFocus(false);
	}

	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) ? true : this.field_212352_g.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}

	public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
		return this.field_212352_g.charTyped(p_charTyped_1_, p_charTyped_2_);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void render(int mouseX, int mouseY, float partialTicks) {}

	/**
	 * Called back by selectionList when we call its drawScreen method, from ours.
	 */
	public void setVersionTooltip(String p_184861_1_) {}

	public void selectWorld(@Nullable SelectionListEntryBase selectionListEntryBase) {}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	public void onGuiClosed() {
		if (this.selectionList != null) {
			this.selectionList.getChildren().forEach(SelectionListEntryBase::close);
		}

	}
}
