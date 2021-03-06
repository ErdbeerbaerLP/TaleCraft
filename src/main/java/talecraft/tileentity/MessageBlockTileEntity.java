package talecraft.tileentity;

import com.google.gson.JsonParseException;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntitySelector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import talecraft.blocks.TCTileEntity;
import talecraft.invoke.IInvoke;

import java.util.List;

public class MessageBlockTileEntity extends TCTileEntity {
    private String playerSelector;
    private String message;
    private boolean tellraw;

    public MessageBlockTileEntity() {
        playerSelector = "@a";
        message = "Hi";
        tellraw = false;
    }

    @Override
    public void commandReceived(String command, NBTTagCompound data) {
        if (command.equals("trigger")) {
            trigger();
            return;
        }

        // fall trough
        super.commandReceived(command, data);
    }

    private void trigger() {
        if (this.world.isRemote) {
            return;
        }

        if (message == null || message.isEmpty()) {
            return;
        }

        ITextComponent[] textComponent;

        // compose chat message depending on the TELLRAW flag
        if (tellraw) {
            // chat message consists of a raw json message
            try {
                textComponent = new ITextComponent[]{ITextComponent.Serializer.jsonToComponent(message)};
            } catch (JsonParseException e) {
                Throwable throwable = ExceptionUtils.getRootCause(e);
                e.printStackTrace();
                throwable.printStackTrace();
                return;
            }
        } else {
            // is there a line break in the message?
            if (message.contains("\\")) {
                // split the message into multiple lines
                String[] lines = StringUtils.split(message, "\\");
                textComponent = new ITextComponent[lines.length];

                for (int i = 0; i < lines.length; i++) {
                    textComponent[i] = new TextComponentString(lines[i]);
                }
            } else {
                textComponent = new ITextComponent[]{new TextComponentString(message)};
            }
        }

        List<EntityPlayer> players = null;
        try {
            players = EntitySelector.matchEntities(this, playerSelector, EntityPlayer.class);
        } catch (CommandException e) {
            e.printStackTrace();
        }

        if (players == null) {
            return;
        }

        if (players.isEmpty()) {
            return;
        }

        // SEND THE MESSAGE(S) TO ALL PLAYERS
        for (EntityPlayer player : players) {
            for (ITextComponent component : textComponent) {
                player.sendMessage(component);
            }
        }
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
    public String getName() {
        return "MessageBlock@" + this.getPos();
    }

    @Override
    public void readFromNBT_do(NBTTagCompound comp) {
        this.playerSelector = comp.getString("playerSelector");
        this.message = comp.getString("message");
        this.tellraw = comp.getBoolean("tellraw");
    }

    @Override
    public NBTTagCompound writeToNBT_do(NBTTagCompound comp) {
        comp.setString("playerSelector", playerSelector);
        comp.setString("message", message);
        comp.setBoolean("tellraw", tellraw);
        return comp;
    }

    public String getPlayerSelector() {
        return playerSelector;
    }

    public String getMessage() {
        return message;
    }

    public boolean getTellRaw() {
        return tellraw;
    }

}
