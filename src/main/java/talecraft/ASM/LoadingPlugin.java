package talecraft.ASM;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

// To use "field_110444_H" instead of "K"
@IFMLLoadingPlugin.SortingIndex(1001)
public class LoadingPlugin implements IFMLLoadingPlugin{

	@Override
	public String[] getASMTransformerClass() {
		// TODO Auto-generated method stub
		return new String[] {"talecraft.ASM.ClassTransformer"};
	}

	@Override
	public String getModContainerClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAccessTransformerClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
