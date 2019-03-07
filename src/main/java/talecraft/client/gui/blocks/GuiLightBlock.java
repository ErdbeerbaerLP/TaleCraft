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
import talecraft.network.StringNBTCommandPacket;

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
		addButton(new GuiButtonExt(0, 2, 40, 100, 10, "Toggle"));
		addButton(new GuiButtonExt(1, 2 + 100 + 2, 40, 50, 10, "Enable"));
		addButton(new GuiButtonExt(2, 2 + 100 + 2 + 50 + 2, 40, 50, 10, "Disable"));
		final ISlider sliderCallback = new ISlider() {
			
			@Override
			public void onChangeSliderValue(GuiSlider slider) {
				// TODO Auto-generated method stub
				int newValue = (int) (slider.getValue() * 16);
				String commandString = ClientNetworkHandler.makeBlockCommand(pos);
				NBTTagCompound commandData = new NBTTagCompound();
				commandData.setString("command", "set");
				commandData.setInt("lightValue", MathHelper.clamp(newValue, 0, 15));
				TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
			}
		};
		light_slider = new GuiSlider(3, 2, 16, "Light: ", 0, 15, tileEntity.getLightValue(), sliderCallback);
	}
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		super.render(mouseX, mouseY, partialTicks);
		drawCenteredString(fontRenderer, "Light @ "+ pos.getX()+" " + pos.getY() + " " + pos.getZ(), 2, 2, 16777215);
		light_slider.render(mouseX, mouseY, partialTicks);
	}
	/*
	@Override
	public void buildGui() {
		final BlockPos position = tileEntity.getPos();
		addComponent(new QADLabel("Light @ " + position.getX() + " " + position.getY() + " " + position.getZ(), 2, 2));

		final QADSlider slider = addComponent(new QADSlider(new DefaultSliderModel(tileEntity.getLightValue(), 15)));
		slider.setX(2);
		slider.setY(16);
		slider.setSliderAction(new Runnable() {
			@Override public void run() {
				int newValue = (int) (slider.getSliderValue() * 16);
				String commandString = ClientNetworkHandler.makeBlockCommand(position);
				NBTTagCompound commandData = new NBTTagCompound();
				commandData.setString("command", "set");
				commandData.setInteger("lightValue", MathHelper.clamp(newValue, 0, 15));
				TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
			}
		});

		QADButton buttonToggle = QADFACTORY.createButton("Toggle", 2, 40, 100, new Runnable() {
			@Override public void run() {
				String commandString = ClientNetworkHandler.makeBlockCommand(position);
				NBTTagCompound commandData = new NBTTagCompound();
				commandData.setString("command", "toggle");
				TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
			}
		});
		addComponent(buttonToggle);

		QADButton buttonEnable = QADFACTORY.createButton("Enable", 2 + 100 + 2, 40, 50, new Runnable() {
			@Override public void run() {
				String commandString = ClientNetworkHandler.makeBlockCommand(position);
				NBTTagCompound commandData = new NBTTagCompound();
				commandData.setString("command", "on");
				TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
			}
		});
		addComponent(buttonEnable);

		QADButton buttonDisable = QADFACTORY.createButton("Disable", 2 + 100 + 2 + 50 + 2, 40, 50, new Runnable() {
			@Override public void run() {
				String commandString = ClientNetworkHandler.makeBlockCommand(position);
				NBTTagCompound commandData = new NBTTagCompound();
				commandData.setString("command", "off");
				TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
			}
		});
		addComponent(buttonDisable);
	}*/

}
