package talecraft.client;

public class ClientProxy {
	public static final ClientSettings settings = new ClientSettings();
	private ClientRenderer clientRenderer;
	private InfoBar infoBarInstance;
	private InvokeTracker invokeTracker;
	private ClientNetworkHandler netHandler;
	public ClientProxy() {
		clientRenderer = new ClientRenderer(this);
		clientRenderer.preInit();
		infoBarInstance = new InfoBar();
		invokeTracker = new InvokeTracker();
		netHandler = new ClientNetworkHandler();
	}

	public ClientRenderer getRenderer() {
		return clientRenderer;
	}
	public InfoBar getInfoBar() {
		// TODO Auto-generated method stub
		return infoBarInstance;
	}
	public InvokeTracker getInvokeTracker() {
		// TODO Auto-generated method stub
		return invokeTracker;
	}

	public ClientNetworkHandler getNetworkHandler() {
		return netHandler;
	}
	
}
