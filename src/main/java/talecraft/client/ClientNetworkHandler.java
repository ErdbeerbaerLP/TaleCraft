package talecraft.client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import talecraft.TaleCraft;
import talecraft.network.StringNBTCommandPacket;

public class ClientNetworkHandler {

	public ClientNetworkHandler() {
	}

	public void handleClientCommand(final String command, final NBTTagCompound data) {

		if(command.equals("client.network.join")) {
			TaleCraft.logger.info("Sending TaleCraft data to server...");

			String tccommand = "join acknowledged";
			NBTTagCompound tcdata = new NBTTagCompound();
			TaleCraft.network.sendToServer(new StringNBTCommandPacket(tccommand, tcdata));

			ClientProxy.settings.send();
			return;
		}

		if(command.equals("client.debug.track.invoke")) {
//			proxy.getInvokeTracker().trackInvoke(data);
			return;
		}

//		if(command.equals("client.gui.editor.entity")) {
//			final UUID uuid = UUID.fromString(data.getString("entityUUID"));
//			final NBTTagCompound entity = data.getCompound("entityData");
//
//			// Open the GUI in the next tick.
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				@Override public void run() {
//					RemoteEntityDataLink dataLink = new RemoteEntityDataLink() {
//						UUID entityUUID = uuid;
//						@Override public void updateData(NBTTagCompound entityData) {
//							NBTTagCompound compound = new NBTTagCompound();
//							compound.setString("entityUUID", entityUUID.toString());
//							compound.setTag("entityData", entityData);
//
//							String cmd = "server.data.entity.merge";
//							StringNBTCommandPacket command = new StringNBTCommandPacket(cmd, compound);
//							TaleCraft.network.sendToServer(command);
//						}
//					};
//					ClientProxy.mc.displayGuiScreen(new GuiEntityEditor(entity, dataLink));
//				}
//			});
//			return;
//		}

		if(command.equals("client.gui.openurl")) {
			final String url = data.getString("url");

			// This is possibly a stupid idea...
			if(data.getBoolean("force")) {
				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}

			// Open the GUI in the next tick.
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				@Override public void run() {
					GuiConfirmOpenLink gui = new GuiConfirmOpenLink(new GuiYesNoCallback() {
						@Override public void confirmResult(boolean result, int id) {
							if (id == 31102009) {
								if (result) {
									try {
										Desktop.getDesktop().browse(new URI(url));
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (URISyntaxException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								Minecraft.getInstance().displayGuiScreen(null);
							}
						}
					}, url, 31102009, true);

					Minecraft.getInstance().displayGuiScreen(gui);
				}
			});
			return;
		}



//		if(command.equals("item.copy.trigger")) {
//			Minecraft.getInstance().addScheduledTask(new Runnable() {
//				@Override
//				public void run() {
//					CopyItem copy = TaleCraftItems.copy;
//					// ItemStack stack = new ItemStack(copy);
//					copy.onItemRightClick(ClientProxy.mc.world, ClientProxy.mc.player, EnumHand.MAIN_HAND);
//				}
//			});
//		}

//		if(command.equals("client.render.renderable.push")) {
//			ITemporaryRenderable renderable = PushRenderableFactory.parsePushRenderableFromNBT(data);
//			if(renderable != null && proxy.isBuildMode()) {
//				proxy.getRenderer().addTemporaryRenderer(renderable);
//			}
//			return;
//		}

//		if(command.equals("client.render.renderable.clear")) {
//			proxy.getRenderer().clearTemporaryRenderers();
//			return;
//		}

		//		if(command.equals("switchShader") && Boolean.FALSE.booleanValue()) {
		//			final String sh = data.getString("shaderName");
		//			clientTickQeue.offer(new Runnable() {
		//				String shader = sh;
		//				@Override
		//				public void run() {
		//					System.out.println("SWITCH : " + shader);
		//
		//					Field[] fields = mc.entityRenderer.getClass().getDeclaredFields();
		//					Field shaderResourceLocations = null;
		//					for(Field field : fields) {
		//						System.out.println("entityRenderer."+field.getName() + " : " + field.getType());
		//					}
		//				}
		//			});
		//			return;
		//		}

		TaleCraft.logger.info("Received Command -> " + command + ", with data: " + data);
		// XXX: Implement more Server->Client commands.

	}

	public static final String makeBlockDataMergeCommand(BlockPos position) {
		return "server.data.block.merge:"+position.getX() + " " + position.getY() + " " + position.getZ();
	}

	public static final String makeBlockCommand(BlockPos position) {
		return "server.data.block.command:"+position.getX() + " " + position.getY() + " " + position.getZ();
	}

}
