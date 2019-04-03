package talecraft.client.gui.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.client.config.GuiSlider.ISlider;
import talecraft.TaleCraft;
import talecraft.blocks.tileentity.DelayBlockTileEntity;
import talecraft.client.ClientNetworkHandler;
import talecraft.client.gui.TCGuiScreen;
import talecraft.client.gui.components.GuiLabel;
import talecraft.client.gui.invoke.BlockInvokeHolder;
import talecraft.client.gui.invoke.InvokePanelBuilder;
import talecraft.network.packets.StringNBTCommandPacket;
import talecraft.util.ArrayListHelper;

public class GuiDelayBlock extends TCGuiScreen {
	DelayBlockTileEntity tileEntity;
	GuiSlider sl;

	public GuiDelayBlock(DelayBlockTileEntity tileEntity) {
		
		this.tileEntity = tileEntity;
	}

	@Override
	public void initGui() {
		final BlockPos position = tileEntity.getPos();

		addComponent(new GuiLabel(this.fontRenderer, ArrayListHelper.createArrayListString("Delay Block @ " + position.getX() + " " + position.getY() + " " + position.getZ()), 2, 2));
		InvokePanelBuilder.build(this, 2, 16, tileEntity.getInvoke(), new BlockInvokeHolder(position, "triggerInvoke"), InvokePanelBuilder.INVOKE_TYPE_EDIT_ALLOWALL);

		final int maximum = 20 * 10;
		final ISlider s = new ISlider() {

			@Override
			public void onChangeSliderValue(GuiSlider slider) {
				int newValue = (int) slider.getValue();
				String commandString = ClientNetworkHandler.makeBlockCommand(position);
				NBTTagCompound commandData = new NBTTagCompound();
				commandData.setString("command", "set");
				commandData.setInt("delay", MathHelper.clamp(newValue, 0, maximum));
				TaleCraft.network.sendToServer(new StringNBTCommandPacket(commandString, commandData));
			}
		};
		sl = new GuiSlider(-1, 2, 16+2+20+2, "Delay in ticks: ", 0, maximum, tileEntity.getDelayValue(), s) {
			@Override
			public void render(int mouseX, int mouseY, float partial) {
				// TODO Auto-generated method stub
				super.render(mouseX, mouseY, partial);
			}
		};
		sl.setWidth(width-2-2);
		sl.showDecimal = false;
		addComponent(sl);
	}
	@Override
	public void tick() {
		sl.setWidth(width-2-2);
	}

}
