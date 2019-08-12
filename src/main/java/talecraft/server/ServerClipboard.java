package talecraft.server;

import com.google.common.collect.Maps;
import talecraft.clipboard.ClipboardItem;

import java.util.Map;

public class ServerClipboard {
    private final Map<String, ClipboardItem> items;

    public ServerClipboard() {
        items = Maps.newHashMap();
    }

    public ClipboardItem get(String name) {
        return items.get(name);
    }

    public void put(String name, ClipboardItem item) {
        items.put(name, item);
    }

}
