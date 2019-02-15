package talecraft.client.gui.replaced_guis.map.download;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DownloadableMap {
	public String name = "Unknown";
	public String description = "Unknown";
	public String author = "Unknown";
	public URL dlURL = null;
	public boolean hasScripts;
	public boolean additionalModsRequired = false;
	public List<DownloadableMod> additionalMods = new ArrayList<DownloadableMod>();
	public String mapURL = "";
	public String mapVersion = "";
	public String mapUUIDs = "-";
	public DownloadableMap(Entry<String, JsonElement> mapJson) {
		// TODO Auto-generated constructor stub
		this.name = mapJson.getKey();
		final JsonObject mapobj = mapJson.getValue().getAsJsonObject();
		if(mapobj.has("desc")) this.description = mapobj.get("desc").getAsString();
		if(mapobj.has("author")) {
			this.author = mapobj.get("author").getAsString();
			
		}
		if(mapobj.has("mapurl")) this.mapURL = mapobj.get("mapurl").getAsString();
		this.hasScripts = (mapobj.has("scripts") && mapobj.get("scripts").getAsBoolean());
		if(mapobj.has("tc-version")) this.mapVersion = mapobj.get("tc-version").getAsString();
		if(mapobj.has("dlurl")) 
			try {
				this.dlURL = new URL(mapobj.get("dlurl").getAsString());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(mapobj.has("additionalMods") && !mapobj.get("additionalMods").isJsonNull()) {
			mapobj.get("additionalMods").getAsJsonObject().entrySet().forEach(new Consumer<Entry<String,JsonElement>>() {

				@Override
				public void accept(Entry<String, JsonElement> mod) {
					// TODO Auto-generated method stub
					DownloadableMap.this.additionalMods.add(new DownloadableMod(mod));
				}
			});
			this.additionalModsRequired = true;
		}
	}
}
