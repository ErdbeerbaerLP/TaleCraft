package talecraft.client;

import talecraft.client.render.renderables.SelectionBoxRenderer;

public class ClientProxy {
	public static final ClientSettings settings = new ClientSettings();
	private ClientRenderer clientRenderer;
	private InfoBar infoBarInstance;
	private InvokeTracker invokeTracker;
	
	public ClientProxy() {
		clientRenderer = new ClientRenderer(this);
		clientRenderer.preInit();
		infoBarInstance = new InfoBar();
		invokeTracker = new InvokeTracker();
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
}
