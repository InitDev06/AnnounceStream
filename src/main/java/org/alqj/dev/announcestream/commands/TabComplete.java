package org.alqj.dev.announcestream.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String la, String[] args) {
        if(args.length >= 1){
            if(cmd.getName().equalsIgnoreCase("announcestream")){
                if(sender.hasPermission("announcestream.cmd.reload") && sender.hasPermission("announcestream.cmd.commands")){
                    return Arrays.asList("commands", "reload");
                }
            }
        }
        return null;
    }
}
