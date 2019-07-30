package talecraft.invoke;

public interface IScriptInvoke extends IInvoke {
    String getScriptName();

    String getScript();

    void reloadScript();
}
