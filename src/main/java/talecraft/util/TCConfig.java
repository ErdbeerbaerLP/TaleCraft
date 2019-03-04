package talecraft.util;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class TCConfig {
	
	public static BooleanValue USE_VERSION_CHECKER;
	public static final ForgeConfigSpec CONFIG_SPEC;
	public static final TCConfig CONFIG;
	static
	{
		Pair<TCConfig,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(TCConfig::new);
		System.out.println("Loading clientside config file...");
		CONFIG = specPair.getLeft();
		CONFIG_SPEC = specPair.getRight();
		
	}
	
	public TCConfig(ForgeConfigSpec.Builder builder) {
		// TODO Auto-generated constructor stub
		builder.comment("TaleCraft config").push("TaleCraft");
		USE_VERSION_CHECKER = builder.comment("Should TaleCraft check for updates?").define("UpdateCheck", true);
	}

}
