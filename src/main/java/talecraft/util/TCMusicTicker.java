package talecraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;

public class TCMusicTicker extends MusicTicker {

	private final Minecraft mc;
	
	public TCMusicTicker(Minecraft mc) {
		super(mc);
		this.mc = mc;
	}
	@Override
	public void play(MusicType song) {
		if(mc.world != null && mc.player != null && (mc.player.getEntityData().getBoolean("no-music") /* || !TaleCraft.proxy.asClient().gamerules.getBoolean("tc_playDefaultMusic")*/)){
			return;
		}
		super.play(song);
	}
	
	@Override
	public void tick() {
		if(mc.world != null && mc.player != null && (mc.player.getEntityData().getBoolean("no-music") /* || !TaleCraft.proxy.asClient().gamerules.getBoolean("tc_playDefaultMusic")*/)){
			// stopMusic(); TODO
		}
		super.tick();
	}

}
