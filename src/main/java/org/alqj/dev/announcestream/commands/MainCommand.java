package org.alqj.dev.announcestream.commands;

import org.alqj.dev.announcestream.AnnounceStream;
import org.alqj.dev.announcestream.color.Msg;
import org.alqj.dev.announcestream.config.Config;
import org.alqj.dev.announcestream.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Optional;

public class MainCommand implements CommandExecutor {

    private final AnnounceStream as;
    private final ConsoleCommandSender log;
    private Sound sound;
    private Sound sound1;
    private int volume;
    private int pitch;

    public MainCommand(AnnounceStream as){
        Optional<XSound> xs = XSound.matchXSound(Config.getFile().getString("options.sounds.permission"));
        Optional<XSound> xs1 = XSound.matchXSound(Config.getFile().getString("options.sounds.reload"));
        if(xs.isPresent() || xs1.isPresent()){
            this.sound = xs.get().parseSound();
            this.sound1 = xs1.get().parseSound();
        } else {
            this.sound = XSound.ENTITY_ITEM_BREAK.parseSound();
            this.sound1 = XSound.UI_BUTTON_CLICK.parseSound();
        }
        this.volume = Config.getFile().getInt("options.sounds.volume");
        this.pitch = Config.getFile().getInt("options.sounds.pitch");
        this.log = Bukkit.getConsoleSender();
        this.as = as;
    }

    private Sound getSound(){ return sound; }

    private Sound getSound1(){ return sound1; }

    private int getVolume(){ return volume; }

    private int getPitch(){ return pitch; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String la, String[] args) {
        FileConfiguration config = Config.getFile();
        String prefix = config.getString("options.messages.prefix");
        if(sender instanceof Player){
            final Player player = (Player) sender;
            if(args.length == 0){
                player.sendMessage(Msg.color(prefix + "&7 Running on version &6" + as.getVersionId() + "&7 by &a" + as.getDeveloperName()));
                return true;
            }

            if(args[0].equalsIgnoreCase("commands")){
                if(player.hasPermission("announcestream.cmd.commands")){
                    String text = config.getString("options.messages.list_cmds");
                    String[] textSplit = text.split("\n");
                    for(int i = 0 ; i < textSplit.length ; i++){
                        String commands = textSplit[i];
                        commands = Msg.color(commands);
                        player.sendMessage(commands);
                    }
                } else {
                    String message = config.getString("options.messages.not_permission");
                    message = message.replace("<prefix>", prefix);
                    if(config.getBoolean("options.sounds.reproduce")) execute(player);
                    player.sendMessage(Msg.color(message));
                }
                return true;
            }

            if(args[0].equalsIgnoreCase("reload")){
                if(player.hasPermission("announcestream.cmd.reload")){
                    long RELOAD = System.currentTimeMillis();
                    Config.reload();
                    String message = config.getString("options.messages.reload");
                    message = message.replace("<ms>", (System.currentTimeMillis() - RELOAD) + "");
                    message = message.replace("<prefix>", prefix);
                    if(config.getBoolean("options.sounds.reproduce")) execute1(player);
                    player.sendMessage(Msg.color(message));
                } else {
                    String message = config.getString("options.messages.not_permission");
                    message = message.replace("<prefix>", prefix);
                    if(config.getBoolean("options.sounds.reproduce")) execute(player);
                    player.sendMessage(Msg.color(message));
                }
                return true;
            }

            String message = config.getString("options.messages.not_command");
            message = message.replace("<prefix>", prefix);
            player.sendMessage(Msg.color(message));
            return false;

        } else {
            if(args.length == 0){
                log.sendMessage(Msg.color(prefix + "&7 Running on version &6" + as.getVersionId() + "&7 by &a" + as.getDeveloperName()));
                return true;
            }

            if(args[0].equalsIgnoreCase("commands")){
                if(log.hasPermission("announcestream.cmd.commands")){
                    String text = config.getString("options.messages.list_cmds");
                    String[] textSplit = text.split("\n");
                    for(int i = 0 ; i < textSplit.length ; i++){
                        String commands = textSplit[i];
                        commands = Msg.color(commands);
                        log.sendMessage(commands);
                    }
                } else {
                    String message = config.getString("options.messages.not_permission");
                    message = message.replace("<prefix>", prefix);
                    log.sendMessage(Msg.color(message));
                }
                return true;
            }

            if(args[0].equalsIgnoreCase("reload")){
                if(log.hasPermission("announcestream.cmd.reload")){
                    long RELOAD = System.currentTimeMillis();
                    Config.reload();
                    String message = config.getString("options.messages.reload");
                    message = message.replace("<ms>", (System.currentTimeMillis() - RELOAD) + "");
                    message = message.replace("<prefix>", prefix);
                    log.sendMessage(Msg.color(message));
                } else {
                    String message = config.getString("options.messages.not_permission");
                    message = message.replace("<prefix>", prefix);
                    log.sendMessage(Msg.color(message));
                }
                return true;
            }

            String message = config.getString("options.messages.not_command");
            message = message.replace("<prefix>", prefix);
            log.sendMessage(Msg.color(message));
            return false;
        }
    }

    private void execute(Player player){ player.playSound(player.getLocation(), getSound(), getVolume(), getPitch()); }

    private void execute1(Player player){ player.playSound(player.getLocation(), getSound1(), getVolume(), getPitch()); }
}
