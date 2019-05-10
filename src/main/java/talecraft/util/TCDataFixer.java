package talecraft.util;

import java.util.Map;

import com.google.common.collect.Maps;
import com.mojang.datafixers.schemas.Schema;

import net.minecraft.nbt.NBTTagCompound;
import talecraft.TaleCraft;

// Adapted from: net.minecraft.util.datafix.fixes.TileEntityId
public class TCDataFixer extends Schema {


	public TCDataFixer(int versionKey, Schema parent) {
		super(versionKey, parent);
		// TODO Auto-generated constructor stub
	}

	private static final Map<String, String> OLD_TO_NEW_ID_MAP = Maps.newHashMap();
    
    static {
    	
    	OLD_TO_NEW_ID_MAP.put("minecraft:tc_lockeddoorblock", TaleCraft.MOD_ID + ":lockeddoorblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_killblock", TaleCraft.MOD_ID + ":killblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_clockblock", TaleCraft.MOD_ID + ":clockblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_redstone_trigger", TaleCraft.MOD_ID + ":redstone_trigger");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_redstone_activator", TaleCraft.MOD_ID + ":redstone_activator");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_relayblock", TaleCraft.MOD_ID + ":relayblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_scriptblock", TaleCraft.MOD_ID + ":scriptblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_updatedetectorblock", TaleCraft.MOD_ID + ":updatedetectorblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_storageblock", TaleCraft.MOD_ID + ":storageblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_emitterblock", TaleCraft.MOD_ID + ":emitterblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_imagehologramblock", TaleCraft.MOD_ID + ":imagehologramblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_collisiontriggerblock", TaleCraft.MOD_ID + ":collisiontriggerblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_lightblock", TaleCraft.MOD_ID + ":lightblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_hiddenblock", TaleCraft.MOD_ID + ":hiddenblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_messageblock", TaleCraft.MOD_ID + ":messageblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_inverterblock", TaleCraft.MOD_ID + ":inverterblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_memoryblock", TaleCraft.MOD_ID + ":memoryblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_triggerfilterblock", TaleCraft.MOD_ID + ":triggerfilterblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_delayblock", TaleCraft.MOD_ID + ":delayblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_urlblock", TaleCraft.MOD_ID + ":urlblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_summonblock", TaleCraft.MOD_ID + ":summonblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_musicblock", TaleCraft.MOD_ID + ":musicblock");
        OLD_TO_NEW_ID_MAP.put("minecraft:tc_camerablock", TaleCraft.MOD_ID + ":camerablock");
        
    	OLD_TO_NEW_ID_MAP.put("tc_lockeddoorblock", TaleCraft.MOD_ID + ":lockeddoorblock");
        OLD_TO_NEW_ID_MAP.put("tc_killblock", TaleCraft.MOD_ID + ":killblock");
        OLD_TO_NEW_ID_MAP.put("tc_clockblock", TaleCraft.MOD_ID + ":clockblock");
        OLD_TO_NEW_ID_MAP.put("tc_redstone_trigger", TaleCraft.MOD_ID + ":redstone_trigger");
        OLD_TO_NEW_ID_MAP.put("tc_redstonetrigger", TaleCraft.MOD_ID + ":redstone_trigger");
        OLD_TO_NEW_ID_MAP.put("tc_redstone_activator", TaleCraft.MOD_ID + ":redstone_activator");
        OLD_TO_NEW_ID_MAP.put("tc_relayblock", TaleCraft.MOD_ID + ":relayblock");
        OLD_TO_NEW_ID_MAP.put("tc_scriptblock", TaleCraft.MOD_ID + ":scriptblock");
        OLD_TO_NEW_ID_MAP.put("tc_updatedetectorblock", TaleCraft.MOD_ID + ":updatedetectorblock");
        OLD_TO_NEW_ID_MAP.put("tc_storageblock", TaleCraft.MOD_ID + ":storageblock");
        OLD_TO_NEW_ID_MAP.put("tc_emitterblock", TaleCraft.MOD_ID + ":emitterblock");
        OLD_TO_NEW_ID_MAP.put("tc_imagehologramblock", TaleCraft.MOD_ID + ":imagehologramblock");
        OLD_TO_NEW_ID_MAP.put("tc_collisiontriggerblock", TaleCraft.MOD_ID + ":collisiontriggerblock");
        OLD_TO_NEW_ID_MAP.put("tc_lightblock", TaleCraft.MOD_ID + ":lightblock");
        OLD_TO_NEW_ID_MAP.put("tc_hiddenblock", TaleCraft.MOD_ID + ":hiddenblock");
        OLD_TO_NEW_ID_MAP.put("tc_messageblock", TaleCraft.MOD_ID + ":messageblock");
        OLD_TO_NEW_ID_MAP.put("tc_inverterblock", TaleCraft.MOD_ID + ":inverterblock");
        OLD_TO_NEW_ID_MAP.put("tc_memoryblock", TaleCraft.MOD_ID + ":memoryblock");
        OLD_TO_NEW_ID_MAP.put("tc_triggerfilterblock", TaleCraft.MOD_ID + ":triggerfilterblock");
        OLD_TO_NEW_ID_MAP.put("tc_delayblock", TaleCraft.MOD_ID + ":delayblock");
        OLD_TO_NEW_ID_MAP.put(":tc_urlblock", TaleCraft.MOD_ID + ":urlblock");
        OLD_TO_NEW_ID_MAP.put("tc_summonblock", TaleCraft.MOD_ID + ":summonblock");
        OLD_TO_NEW_ID_MAP.put("tc_musicblock", TaleCraft.MOD_ID + ":musicblock");
        OLD_TO_NEW_ID_MAP.put("tc_camerablock", TaleCraft.MOD_ID + ":camerablock");
    }

    public int getFixVersion() {
        return 1;
    }

    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
    	String oldID = compound.getString("id");
        String newID = OLD_TO_NEW_ID_MAP.get(oldID);

        if(newID != null) {
        	TaleCraft.logger.info("Converted tile entity ID. Old ID: " + oldID + ", new ID: " + newID);
            compound.setString("id", newID);
        }

        return compound;
    }
}