package talecraft.client.gui.blocks;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.client.config.GuiSlider.ISlider;
import talecraft.TaleCraft;
import talecraft.blocks.tileentity.LightBlockTE;
import talecraft.client.ClientNetworkHandler;
import talecraft.client.gui.GuiColors;
import talecraft.network.packets.StringNBTCommandPacket;

public class GuiLightBlock extends GuiScreen {
	LightBlockTE tileEntity;
	final BlockPos pos;
	
	public GuiLightBlock(LightBlockTE tileEntity) {
		this.tileEntity = tileEntity;
		pos =  tileEntity.getPos();
	}
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	GuiSlider light_slider;
	@Override
	protected void initGui() {
		addButton(new GuiButtonExt(0, 2, 40, 100, 10, "Toggle"){
			@Override
			public void onClick(double mouseX, double mouseY) {
				String commandString = ClientNetworkHandler.makeBlockCommand(pos);
				NBTTagCompound commandData = new NBTTagCompound();
				commandData.setString("command", "toggle");
				TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
			}
		});
		addButton(new GuiButtonExt(1, 2 + 100 + 2, 40, 50, 10, "Enable"){
			@Override public void onClick(double mouseX, double mouseY) {
				String commandString = ClientNetworkHandler.makeBlockCommand(pos);
				NBTTagCompound commandData = new NBTTagCompound();
				commandData.setString("command", "on");
				TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
			}
		});
		addButton(new GuiButtonExt(2, 2 + 100 + 2 + 50 + 2, 40, 50, 10, "Disable"){
			@Override public void onClick(double mouseX, double mouseY) {
			String commandString = ClientNetworkHandler.makeBlockCommand(pos);
			NBTTagCompound commandData = new NBTTagCompound();
			commandData.setString("command", "off");
			TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
		}
	});
		final ISlider sliderCallback = new ISlider() {
			
			@Override
			public void onChangeSliderValue(GuiSlider slider) {
				// TODO Auto-generated method stub
				int newValue = (int) (slider.getValue());
				String commandString = ClientNetworkHandler.makeBlockCommand(pos);
				NBTTagCompound commandData = new NBTTagCompound();
				commandData.setString("command", "set");
				commandData.setInt("lightValue", MathHelper.clamp(newValue, 0, 15));
				TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
			}
		};
		light_slider = new GuiSlider(3, 2, 16, "Light: ", 0, 15, tileEntity.getLightValue(), sliderCallback);
		light_slider.showDecimal = false;
	}
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		super.render(mouseX, mouseY, partialTicks);
		drawString(fontRenderer, "Light @ "+ pos.getX()+" " + pos.getY() + " " + pos.getZ(), 2, 2, GuiColors.WHITE);
		light_slider.render(mouseX, mouseY, partialTicks);
	}
	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		// TODO Auto-generated method stub
		light_slider.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}
	@Override
	public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_,
			double p_mouseDragged_6_, double p_mouseDragged_8_) {
		light_slider.mouseDragged(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_);
		return super.mouseDragged(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_,
				p_mouseDragged_8_);
	}
	@Override
	public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
		// TODO Auto-generated method stub
		light_slider.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
		return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
	}

}
