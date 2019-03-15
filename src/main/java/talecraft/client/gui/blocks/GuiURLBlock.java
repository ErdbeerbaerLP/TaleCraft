package talecraft.client.gui.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import talecraft.TaleCraft;
import talecraft.blocks.tileentity.URLBlockTileEntity;
import talecraft.client.ClientNetworkHandler;
import talecraft.client.gui.TCGuiScreen;
import talecraft.client.gui.components.GuiLabel;
import talecraft.client.gui.components.GuiButton;
import talecraft.client.gui.components.GuiTextField;
import talecraft.network.packets.StringNBTCommandPacket;
import talecraft.util.ArrayListHelper;

public class GuiURLBlock extends TCGuiScreen {
	URLBlockTileEntity tileEntity;

	GuiTextField textField_url;
	GuiTextField textField_selector;

	public GuiURLBlock(URLBlockTileEntity tileEntity) {
		this.tileEntity = tileEntity;
	}

	@Override
	public void initGui() {
		final BlockPos position = tileEntity.getPos();

		addComponent(new GuiLabel(this.fontRenderer, ArrayListHelper.createArrayListString("URL Block @ " + position.getX() + " " + position.getY() + " " + position.getZ()), 5, 2));

		textField_url = new GuiTextField(fontRenderer, 3, 14+20+4, width-6, 20);
		textField_url.setText(tileEntity.getURL());
		textField_url.setTooltip("The URL to open.");
		addComponent(textField_url);

		textField_selector = new GuiTextField(fontRenderer, 3, 14+20+4+20+4, width-6, 20);
		textField_selector.setText(tileEntity.getSelector());
		textField_selector.setTooltip("Selector to select players.", "Default: @a");
		addComponent(textField_selector);

		GuiButton setDataButton = new GuiButton(2, 14, 60,"Apply"){
			@Override public void onClick() {
				String commandString = ClientNetworkHandler.makeBlockDataMergeCommand(position);
				NBTTagCompound commandData = new NBTTagCompound();

				commandData.setString("selector", textField_selector.getText());
				commandData.setString("url", textField_url.getText());

				TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
				mc.displayGuiScreen(null);
			}
		};
		setDataButton.setTooltip("There is no auto-save, ", "so don't forget to click this button!");
		addComponent(setDataButton);

	}

	@Override
	public void tick() {
		textField_url.setWidth(width-6);
		textField_selector.setWidth(width-6);
	}



}
