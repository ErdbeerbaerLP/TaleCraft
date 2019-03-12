package talecraft.client.gui.invoke;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDebugStick;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import talecraft.client.gui.TCGuiScreen;
import talecraft.client.gui.components.GuiLabel;
import talecraft.client.gui.components.GuiTCButton;
import talecraft.client.gui.components.GuiTCButton.ButtonIcon;
import talecraft.invoke.BlockTriggerInvoke;
import talecraft.invoke.CommandInvoke;
import talecraft.invoke.EnumTriggerState;
import talecraft.invoke.IInvoke;
import talecraft.invoke.NullInvoke;
import talecraft.items.WandItem;
import talecraft.util.ArrayListHelper;

public class InvokePanelBuilder {
	public static final int INVOKE_TYPE_EDIT_ALLOWALL = -1;
	public static final int INVOKE_TYPE_EDIT_ALLOW_NULL = 1;
	public static final int INVOKE_TYPE_EDIT_ALLOW_BLOCKTRIGGER = 2;
//	public static final int INVOKE_TYPE_EDIT_ALLOW_SCRIPTFILE = 4;
//	public static final int INVOKE_TYPE_EDIT_ALLOW_SCRIPTEMBEDDED = 8;
	public static final int INVOKE_TYPE_EDIT_ALLOW_COMMAND = 16;

	/**
	 * Maximum Width: 20 + ?
	 **/
	public static final void build(TCGuiScreen screen, int ox, int oy, IInvoke invoke, final IInvokeHolder holder, int invokeTypeFlags) {

		if(invokeTypeFlags != 0) {
			GuiTCButton button = new GuiTCButton(ox, oy, 20, "", ButtonIcon.INVEDIT) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					// TODO Auto-generated method stub
					super.onClick(mouseX, mouseY);
					new InvokeSwitchAction(invokeTypeFlags, holder, screen).run();
				}
			};
			button.enabled = true;
//			button.setIcon(QADButton.ICON_INVEDIT);
//			button.setTooltip("Change Invoke Type");
			screen.addComponent(button);
			ox += 20 + 2;
		}

		if(invoke == null || invoke instanceof NullInvoke) {
			screen.addComponent(new GuiLabel(screen.getFontRenderer(), ArrayListHelper.createArrayListString("Null Invoke"), 0, 0));
			return;
		}

		if(invoke instanceof CommandInvoke) {
			build_command(screen, ox, oy, (CommandInvoke) invoke, holder);
			return;
		}

		if(invoke instanceof BlockTriggerInvoke) {
			build_blocktrigger(screen, ox, oy, (BlockTriggerInvoke) invoke, holder);
			return;
		}

//		if(invoke instanceof FileScriptInvoke) {
//			build_filescript(screen, ox, oy, (FileScriptInvoke) invoke, holder);
//		}

	}

	private static void build_command(TCGuiScreen screen,
			int ox, int oy, CommandInvoke invoke, final IInvokeHolder holder) {

		final GuiTextField scriptName = new GuiTextField(-1, screen.getFontRenderer(), ox+1, oy+2, 100-2, 20); // QADFACTORY.createTextField(invoke.getCommand(), ox+1, oy+2, 100-2);
		
		scriptName.setMaxStringLength(32700);
		screen.addComponent(scriptName);
		GuiTCButton buttonApply = new GuiTCButton(ox+100+2, oy, 40, "Apply"){
			@Override public void onClick(double mouseX, double mouseY) {
				NBTTagCompound invokeData = new NBTTagCompound();
				String text = scriptName.getText();
				invokeData.setString("type", "CommandInvoke");
				invokeData.setString("command", text);

				holder.sendInvokeUpdate(invokeData);
				
			}
		}; //QADFACTORY.createButton("Apply", ox+100+2, oy, 40, null);
//		buttonApply.setTooltip("Saves the settings.", "There is no auto-save so make","sure to press this button.");
		screen.addComponent(buttonApply.setTooltip("Saves the settings.", "There is no auto-save so make","sure to press this button."));

		//		QADButton buttonExecute = QADFACTORY.createButton("E", ox+100+4+40+2, oy, 20, null);
		//		buttonExecute.setAction(new Runnable() {
		//			@Override public void run() {
		//				holder.sendCommand("execute", null);
		//			}
		//		});
		//		buttonExecute.setTooltip("Prompts the server to","execute the command.");
		//		components.add(buttonExecute);

	}

//	private static void build_filescript(InvokeGuiScreen scree,
//			int ox, int oy, FileScriptInvoke invoke, final IInvokeHolder holder) {
//
//		final QADTextField scriptName = QADFACTORY.createTextField(invoke.getScriptName(), ox+1, oy+2, 100-2);
//		scriptName.setTooltip("The file-name of the script to execute.");
//		scriptName.setMaxStringLength(128);
//		container.addComponent(scriptName);
//
//		QADButton buttonApply = QADFACTORY.createButton("Apply", ox+100+2, oy, 40, null);
//		buttonApply.setAction(new Runnable() {
//			@Override public void run() {
//				NBTTagCompound commandData = new NBTTagCompound();
//				NBTTagCompound invokeData = new NBTTagCompound();
//				commandData.setTag("scriptInvoke", invokeData);
//
//				String text = scriptName.getText();
//				invokeData.setString("type", "FileScriptInvoke");
//				invokeData.setString("scriptFileName", text);
//
//				holder.sendInvokeUpdate(invokeData);
//			}
//		});
//		buttonApply.setTooltip("Saves the settings.", "There is no auto-save, so make","sure to press this button.");
//		container.addComponent(buttonApply);
//
//
//		QADButton buttonReload = QADFACTORY.createButton("R", ox+100+4+40+2, oy, 20, null);
//		buttonReload.setAction(new Runnable() {
//			@Override public void run() {
//				holder.sendCommand("reload", null);
//			}
//		});
//		buttonReload.setTooltip("Prompts the server to","reload the script.");
//		container.addComponent(buttonReload);
//
//
//		QADButton buttonExecute = QADFACTORY.createButton("E", ox+100+4+40+2+20+2, oy, 20, null);
//		buttonExecute.setAction(new Runnable() {
//			@Override public void run() {
//				holder.sendCommand("execute", null);
//			}
//		});
//		buttonExecute.setTooltip("Prompts the server to","execute the script.");
//		container.addComponent(buttonExecute);
//
//
//		QADButton buttonReloadExecute = QADFACTORY.createButton("R+E", ox+100+4+40+2+20+2+20+2, oy, 24, null);
//		buttonReloadExecute.setAction(new Runnable() {
//			@Override public void run() {
//				holder.sendCommand("reloadexecute", null);
//			}
//		});
//		buttonReloadExecute.setTooltip("Prompts the server to reload","and then execute the script.");
//		container.addComponent(buttonReloadExecute);
//
//	}



	private static void build_blocktrigger(TCGuiScreen screen, int ox, int oy, final BlockTriggerInvoke invoke, final IInvokeHolder holder) {
		screen.addComponent(new GuiTCButton(ox, oy, 100, "Set Region") {
			
			
			@Override 
			public void onClick(double mouseX, double mouseY) {
				EntityPlayer player = Minecraft.getInstance().player;
				int[] bounds = WandItem.getBoundsFromPlayerOrNull(player);

				if(bounds == null){
					Minecraft.getInstance().ingameGUI.getChatGUI().addToSentMessages(TextFormatting.RED+"Error: "+TextFormatting.RESET+"Wand selection is invalid.");
					return;
				}

				NBTTagCompound invokeData = new NBTTagCompound();
				invokeData.setString("type", "BlockTriggerInvoke");
				invokeData.setIntArray("bounds", bounds);

				holder.sendInvokeUpdate(invokeData);
				
			}
		}.setTooltip(
				"Sets the region that is triggered",
				"when this invoke is run."
				));

		screen.addComponent(new GuiTCButton(ox+100+2, oy, 20, "", ButtonIcon.PLAY) {
			
			@Override public void onClick(double mouseX, double mouseY) {
				holder.sendCommand("trigger", null);
			}
			
		}.setTooltip("Trigger this invoke."));
		
		GuiTCButton state = new GuiTCButton(ox+100+2+20+2, oy, 50,  "") {
			int ordinal = invoke.getOnOff().getIntValue();

			@Override 
			public void onClick() {
				ordinal++;

				if(ordinal > 1) {
					ordinal = -2;
				}

				NBTTagCompound invokeData = new NBTTagCompound();
				invokeData.setInt("state", ordinal);
				holder.sendInvokeUpdate(invokeData);
			}
			public void render(int mouseX, int mouseY, float partial) {
				this.displayString = EnumTriggerState.get(ordinal).name();
				super.render(mouseX, mouseY, partial);
				
			};
		};
		screen.addComponent(state.setTooltip("The state of the trigger. Default is ON."));
	}

	public static class InvokeSwitchAction implements Runnable {
		int invokeTypeFlags;
		IInvokeHolder holder;
		GuiScreen screen;

		public InvokeSwitchAction(int invokeTypeFlags, IInvokeHolder holder, GuiScreen screen) {
			this.invokeTypeFlags = invokeTypeFlags;
			this.holder = holder;
			this.screen = screen;
		}

		@Override
		public void run() {
			Minecraft.getInstance().displayGuiScreen(new InvokeSwitchGui(invokeTypeFlags, holder, screen));
		}
	}

}
