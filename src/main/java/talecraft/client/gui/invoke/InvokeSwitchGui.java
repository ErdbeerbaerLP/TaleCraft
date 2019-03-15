package talecraft.client.gui.invoke;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;
import talecraft.client.gui.TCGuiScreen;
import talecraft.client.gui.components.GuiLabel;
import talecraft.client.gui.components.GuiButton;
import talecraft.invoke.BlockTriggerInvoke;
import talecraft.invoke.CommandInvoke;
import talecraft.invoke.NullInvoke;
import talecraft.util.ArrayListHelper;

public class InvokeSwitchGui extends TCGuiScreen {
	int invokeTypeFlags;
	IInvokeHolder holder;
	GuiScreen screen;

	public InvokeSwitchGui(int invokeTypeFlags, IInvokeHolder holder, GuiScreen screen) {
		this.invokeTypeFlags = invokeTypeFlags;
		this.holder = holder;
		this.screen = screen;
	}

	@Override
	public void initGui() {
		int xOff = 2;
		int yOff = 16;

		addComponent(new GuiLabel(this.fontRenderer, ArrayListHelper.createArrayListString("Select Invoke Type..."), 4, 4));

		{
			addComponent(new GuiButton(xOff, yOff, TextFormatting.ITALIC+"Cancel") {
				@Override public void onClick() {
					mc.displayGuiScreen(screen);
				}
			});
			yOff += 20;
			yOff += 4;
		}

		if((invokeTypeFlags & InvokePanelBuilder.INVOKE_TYPE_EDIT_ALLOW_NULL) != 0) {
			addComponent(new GuiButton(xOff, yOff,"None"){
				@Override public void onClick() {
					holder.switchInvokeType(NullInvoke.TYPE);
					mc.displayGuiScreen(null);
				}
			});
			yOff += 20;
		}

		if((invokeTypeFlags & InvokePanelBuilder.INVOKE_TYPE_EDIT_ALLOW_BLOCKTRIGGER) != 0) {
			addComponent(new GuiButton(xOff, yOff,"Block Trigger"){
				@Override public void onClick() {
					holder.switchInvokeType(BlockTriggerInvoke.TYPE);
					mc.displayGuiScreen(null);
				}
			});
			yOff += 20;
		}

		if((invokeTypeFlags & InvokePanelBuilder.INVOKE_TYPE_EDIT_ALLOW_COMMAND) != 0) {
			addComponent(new GuiButton(xOff, yOff,"Command") {
				@Override public void onClick() {
					holder.switchInvokeType(CommandInvoke.TYPE);
					mc.displayGuiScreen(null);
				}
			});
			yOff += 20;
		}

//		if((invokeTypeFlags & InvokePanelBuilder.INVOKE_TYPE_EDIT_ALLOW_SCRIPTFILE) != 0) {
//			addComponent(QADFACTORY.createButton("Script File", xOff, yOff, 120, new Runnable() {
//				@Override public void run() {
//					holder.switchInvokeType(FileScriptInvoke.TYPE);
//					mc.displayGuiScreen(null);
//				}
//			}).setTooltip("Invoke that executes a script."));
//			yOff += 20;
//		}

	}

}
