package talecraft.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import talecraft.TaleCraft;
import talecraft.network.packets.FadePacket;

import java.util.Arrays;
import java.util.List;

public class FadeCommand extends TCCommandBase {

    @Override
    public String getName() {
        return "tc_fade";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "<player> [color:ARGB] [time:Ticks] [texture:ResourceLocation]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        int color = 0x000000;
        int time = 40;
        String texture = null;

        List<EntityPlayerMP> players;
        if (args.length > 0) {
            players = EntitySelector.matchEntities(sender, args[0], EntityPlayerMP.class);
            if (args.length > 1) {
                try {
                    color = Integer.parseInt(args[1]);
                    if (args.length > 2) {
                        time = Integer.parseInt(args[2]);
                    }
                } catch (NumberFormatException ex) {
                    throw new NumberInvalidException("Invalid integer!");
                }

                if (args.length > 2) {
                    String[] a = Arrays.copyOfRange(args, 3, args.length);
                    StringBuilder b = new StringBuilder();
                    for (String s : a)
                        b.append(s).append(" ");
                    texture = b.toString().trim();
                }
            }
        } else {
            throw new CommandException("Incorrect usage!");
        }

        for (EntityPlayerMP player : players) {
            TaleCraft.network.sendTo(new FadePacket(color, time, texture), player);
        }

    }

}
