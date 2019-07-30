package talecraft.script.wrappers;

import java.util.List;

public interface IObjectWrapper {

    Object internal();

    List<String> getOwnPropertyNames();

}
