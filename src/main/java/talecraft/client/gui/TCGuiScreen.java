package talecraft.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;


public class TCGuiScreen extends GuiScreen {
	public final List<Gui> components;
	
	public TCGuiScreen() {
		// TODO Auto-generated constructor stub
		components =  new ArrayList<Gui>();
	}
	
	public void addComponent(Gui component) {
		if(component instanceof GuiButton) {
			this.addButton((GuiButton) component);
			return;
		}
		this.components.add(component);
	}
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		drawDefaultBackground();
		super.render(mouseX, mouseY, partialTicks);
		
		for(Gui comp : components) {
			if(comp instanceof GuiTextField) {
				((GuiTextField)comp).drawTextField(mouseX, mouseY, partialTicks);
			}
			if(comp instanceof GuiLabel) {
				((GuiLabel)comp).render(mouseX, mouseY, partialTicks);
			}
		}
	}
	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		// TODO Auto-generated method stub
		for(Gui comp : components) {
			if(comp instanceof GuiTextField) {
				((GuiTextField)comp).mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
			}
		}
		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}
	@Override
	public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
		for(Gui comp : components) {
			if(comp instanceof GuiTextField) {
				((GuiTextField)comp).mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
			}
		}
		return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
	}
	@Override
	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		for(Gui comp : components) {
			if(comp instanceof GuiTextField) {
				((GuiTextField)comp).keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
			}
		}
		return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}
	@Override
	public boolean keyReleased(int p_keyReleased_1_, int p_keyReleased_2_, int p_keyReleased_3_) {
		// TODO Auto-generated method stub
		for(Gui comp : components) {
			if(comp instanceof GuiTextField) {
				((GuiTextField)comp).keyReleased(p_keyReleased_1_, p_keyReleased_2_, p_keyReleased_3_);
			}
		}
		return super.keyReleased(p_keyReleased_1_, p_keyReleased_2_, p_keyReleased_3_);
	}
	@Override
	public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
		for(Gui comp : components) {
			if(comp instanceof GuiTextField) {
				((GuiTextField)comp).charTyped(p_charTyped_1_, p_charTyped_2_);
			}
		}
		return super.charTyped(p_charTyped_1_, p_charTyped_2_);
	}
	public Gui getComponent(int index){
		return components.get(index);
	}
	public FontRenderer getFontRenderer() {
		// TODO Auto-generated method stub
		 return this.fontRenderer;
	}

}
