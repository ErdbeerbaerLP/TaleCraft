package talecraft.blocks.tileentity;

import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import talecraft.TaleCraft;
import talecraft.TaleCraftRegistered;
import talecraft.invoke.EnumTriggerState;
import talecraft.invoke.IInvoke;
import talecraft.network.packets.StringNBTCommandPacketClient;
import talecraft.util.NBTHelper;

public class URLBlockTileEntity extends TCTileEntity {
	String url;
	String selector;

	public URLBlockTileEntity() {
		super(TaleCraftRegistered.TE_URL);
		url = "https://www.reddit.com/r/talecraft/";
		selector = "@a";
	}

	@Override
	public void getInvokes(List<IInvoke> invokes) {
		// none
	}

	@Override
	public void getInvokeColor(float[] color) {
		color[0] = 1.0f;
		color[1] = 1.0f;
		color[2] = 0.8f;
	}




	@Override
	public void readFromNBT_do(NBTTagCompound comp) {
		url = comp.getString("url");
		selector = comp.getString("selector");
	}

	@Override
	public NBTTagCompound writeToNBT_do(NBTTagCompound comp) {
		comp.setString("url", url);
		comp.setString("selector", selector);
		return comp;
	}

	public String getURL() {
		return url;
	}

	public String getSelector() {
		return selector;
	}

	public void trigger(EnumTriggerState triggerState) {
		if(triggerState.getBooleanValue()) {
			List<EntityPlayerMP> players = null;
			try {
				
				players = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();
			} catch (CommandException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringNBTCommandPacketClient command = new StringNBTCommandPacketClient();
			command.command = "client.gui.openurl";
			command.data = NBTHelper.newSingleStringCompound("url",url);

			for(EntityPlayerMP player : players) {
				TaleCraft.network.sendTo(command, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
			}
		}
	}

}
