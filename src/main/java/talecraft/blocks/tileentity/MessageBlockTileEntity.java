package talecraft.blocks.tileentity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.client.Minecraft;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import talecraft.TaleCraftRegistered;
import talecraft.invoke.IInvoke;

public class MessageBlockTileEntity extends TCTileEntity {
//	private String playerSelector;
	private String message;
	private boolean tellraw;

	public MessageBlockTileEntity() {
		super(TaleCraftRegistered.TE_MESSAGE);
//		playerSelector = "@a";
		message = "Hi";
		tellraw = false;
	}

	@Override
	public void commandReceived(String command, NBTTagCompound data) {
		if(command.equals("trigger")) {
			trigger();
			return;
		}

		// fall trough
		super.commandReceived(command, data);
	}

	private void trigger() {
		if(this.world.isRemote) {
			return;
		}

		if(message == null || message.isEmpty()) {
			return;
		}

		ITextComponent[] textComponent = null;

		// compose chat message depending on the TELLRAW flag
		if(tellraw) {
			// chat message consists of a raw json message
			try {
				textComponent = new ITextComponent[]{ITextComponent.Serializer.fromJson(message)};
			} catch (JsonParseException e) {
				Throwable throwable = ExceptionUtils.getRootCause(e);
				e.printStackTrace();
				throwable.printStackTrace();
				return;
			}
		} else {
			// is there a line break in the message?
			if(message.contains("\\")) {
				// split the message into multiple lines
				String[] lines = StringUtils.split(message, "\\");
				textComponent = new ITextComponent[lines.length];

				for(int i = 0; i < lines.length; i++) {
					textComponent[i] = new TextComponentString(lines[i]);
				}
			} else {
				textComponent = new ITextComponent[]{new TextComponentString(message)};
			}
		}

//		List<EntityPlayerMP> players = null;
//		try {
//			players = parse(new StringReader(playerSelector)).selectPlayers(Minecraft.getInstance().player.getCommandSource());
//		} catch (Exception e) {
//			e.printStackTrace(); 
//		}
//
//		if(players == null) {
//			return;
//		}
//
//		if(players.isEmpty()) {
//			return;
//		}
//
//		// SEND THE MESSAGE(S) TO ALL PLAYERS
//		for(EntityPlayerMP player : players) {
//			for(ITextComponent component : textComponent) {
//				player.sendMessage(component);
//			}
//		}
		for(ITextComponent component : textComponent) {
			Minecraft.getInstance().player.sendMessage(component);
		}
	}
	public EntitySelector parse(StringReader p_parse_1_) throws CommandSyntaxException {
	      int i = 0;
	      EntitySelectorParser entityselectorparser = new EntitySelectorParser(p_parse_1_);
	      EntitySelector entityselector = entityselectorparser.parse();
	      return entityselector;
	   }
	@Override
	public void getInvokes(List<IInvoke> invokes) {
		// none
	}

	@Override
	public void getInvokeColor(float[] color) {
		color[0] = 0.90f;
		color[1] = 0.85f;
		color[2] = 0.50f;
	}

	

	@Override
	public void readFromNBT_do(NBTTagCompound comp) {
//		this.playerSelector = comp.getString("playerSelector");
		this.message = comp.getString("message");
		this.tellraw = comp.getBoolean("tellraw");
	}

	@Override
	public NBTTagCompound writeToNBT_do(NBTTagCompound comp) {
//		comp.setString("playerSelector", playerSelector);
		comp.setString("message", message);
		comp.setBoolean("tellraw", tellraw);
		return comp;
	}

//	public String getPlayerSelector() {
//		return playerSelector;
//	}

	public String getMessage() {
		return message;
	}

	public boolean getTellRaw() {
		return tellraw;
	}

}
