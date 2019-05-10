package talecraft.client.gui.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import talecraft.TaleCraft;
import talecraft.blocks.tileentity.MessageBlockTileEntity;
import talecraft.client.ClientNetworkHandler;
import talecraft.client.gui.TCGuiScreen;
import talecraft.client.gui.components.GuiButton;
import talecraft.client.gui.components.GuiLabel;
import talecraft.client.gui.components.GuiTextField;
import talecraft.client.gui.components.TickBox;
import talecraft.network.packets.StringNBTCommandPacket;

public class GuiMessageBlock extends TCGuiScreen {
	MessageBlockTileEntity tileEntity;

	GuiTextField textField_message;
//	GuiTextField textField_selector;

	public GuiMessageBlock(MessageBlockTileEntity tileEntity) {
		this.tileEntity = tileEntity;
	}

	@Override
	public void initGui() {
		final BlockPos position = tileEntity.getPos();

		addComponent(new GuiLabel(fontRenderer,"Message Block @ " + position.getX() + " " + position.getY() + " " + position.getZ(), 2, 7));

		textField_message = new GuiTextField(fontRenderer, 3, 14+20+4, width-6, 20);
		textField_message.setText(tileEntity.getMessage());
		textField_message.setTooltip("The message to send.");
		textField_message.setMaxStringLength(9999);
		addComponent(textField_message);

//		textField_selector = new GuiTextField(fontRenderer, 3, 14+20+4+20+4, width-6, 20);
//		textField_selector.setText(tileEntity.getPlayerSelector());
//		textField_selector.setTooltip("Selector to select players to send the message to.", "Default: @a");
//		addComponent(textField_selector);

		final TickBox tickBox_tellraw = new TickBox(2+60+2, 14);
		tickBox_tellraw.displayString = "Raw";
		tickBox_tellraw.setIsChecked(tileEntity.getTellRaw());
		tickBox_tellraw.setTooltip("Should the message be parsed as raw json?","Default: Off");
		addComponent(tickBox_tellraw);

		GuiButton setDataButton = new GuiButton( 2, 14, 60, "Apply"){
			public void onClick() {
				String commandString = ClientNetworkHandler.makeBlockDataMergeCommand(position);
				NBTTagCompound commandData = new NBTTagCompound();

				// data?
//				commandData.setString("playerSelector", textField_selector.getText());
				commandData.setString("message", textField_message.getText());
				commandData.setBoolean("tellraw", tickBox_tellraw.isChecked());

				TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
				mc.displayGuiScreen(null);
			}
		};
		setDataButton.setTooltip("There is no auto-save, ", "so don't forget to click this button!");
		addComponent(setDataButton);

	}

	@Override
	public void tick() {
		textField_message.setWidth(width-6);
//		textField_selector.setWidth(width-6);
	}

}
