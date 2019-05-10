package talecraft.client.gui.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import talecraft.TaleCraft;
import talecraft.blocks.tileentity.MemoryBlockTileEntity;
import talecraft.client.ClientNetworkHandler;
import talecraft.client.gui.TCGuiScreen;
import talecraft.client.gui.components.GuiButton;
import talecraft.client.gui.components.GuiLabel;
import talecraft.client.gui.invoke.BlockInvokeHolder;
import talecraft.client.gui.invoke.InvokePanelBuilder;
import talecraft.network.packets.StringNBTCommandPacket;

public class GuiMemoryBlock extends TCGuiScreen {
	MemoryBlockTileEntity tileEntity;

	public GuiMemoryBlock(MemoryBlockTileEntity tileEntity) {
		this.tileEntity = tileEntity;
	}

	@Override
	public void initGui() {
		final BlockPos position = tileEntity.getPos();

		addComponent(new GuiLabel(fontRenderer, "Memory Block @ " + position.getX() + " " + position.getY() + " " + position.getZ(), 2, 7));
		InvokePanelBuilder.build(this, 2, 16, tileEntity.getTriggerInvoke(), new BlockInvokeHolder(position, "triggerInvoke"), InvokePanelBuilder.INVOKE_TYPE_EDIT_ALLOWALL);

		addComponent(new GuiButton(2, 16+2+20+2, 60, "Reset") {
			@Override public void onClick() {
				String commandString = ClientNetworkHandler.makeBlockCommand(position);
				NBTTagCompound commandData = new NBTTagCompound();
				commandData.setString("command", "reset");
				TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
				GuiMemoryBlock.this.mc.displayGuiScreen(null);
			}


		});
		addComponent(new GuiLabel(fontRenderer, "Triggered: " + tileEntity.getIsTriggered(), 2+60+2, 16+2+20+2+6));

	}

}
