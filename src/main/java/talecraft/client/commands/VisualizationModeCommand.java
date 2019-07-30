package talecraft.client.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import talecraft.client.ClientRenderer.VisualMode;
import talecraft.proxy.ClientProxy;

public final class VisualizationModeCommand extends CommandBase {
    @Override
    public String getName() {
        return "tcc_vismode";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        final VisualMode mode = VisualMode.valueOf(args[0]);

        ClientProxy.shedule(() -> ClientProxy.proxy.getRenderer().setVisualizationMode(mode));
    }
}