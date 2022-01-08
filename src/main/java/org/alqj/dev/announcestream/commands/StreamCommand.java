package org.alqj.dev.announcestream.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.alqj.dev.announcestream.AnnounceStream;
import org.alqj.dev.announcestream.color.Msg;
import org.alqj.dev.announcestream.config.Config;
import org.alqj.dev.announcestream.util.StringUtil;
import org.alqj.dev.announcestream.xseries.XSound;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Optional;

public class StreamCommand implements CommandExecutor {

    private final AnnounceStream as;
    private final ConsoleCommandSender log;
    private final int CENTER_PX = 154;
    private Sound sound;
    private Sound sound1;
    private Sound sound2;
    private int volume;
    private int pitch;

    public StreamCommand(AnnounceStream as){
        Optional<XSound> xs = XSound.matchXSound(Config.getFile().getString("options.sounds.permission"));
        Optional<XSound> xs1 = XSound.matchXSound(Config.getFile().getString("options.sounds.cooldown"));
        Optional<XSound> xs2 = XSound.matchXSound(Config.getFile().getString("options.sounds.stream_announce"));
        if(xs.isPresent() || xs1.isPresent() || xs2.isPresent()){
            this.sound = xs.get().parseSound();
            this.sound1 = xs1.get().parseSound();
            this.sound2 = xs2.get().parseSound();
        } else {
            this.sound = XSound.ENTITY_ITEM_BREAK.parseSound();
            this.sound1 = XSound.BLOCK_NOTE_BLOCK_PLING.parseSound();
            this.sound2 = XSound.ENTITY_ENDER_DRAGON_FLAP.parseSound();
        }
        this.volume = Config.getFile().getInt("options.sounds.volume");
        this.pitch = Config.getFile().getInt("options.sounds.pitch");
        this.log = Bukkit.getConsoleSender();
        this.as = as;
    }

    private Sound getSound(){ return sound; }

    private Sound getSound1(){ return sound1; }

    private Sound getSound2(){ return sound2; }

    private int getVolume(){ return volume; }

    private int getPitch(){ return pitch; }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String la, String[] args) {
        FileConfiguration config = Config.getFile();
        String prefix = config.getString("options.messages.prefix");
        if(sender instanceof Player){
            final Player player = (Player) sender;
            if(player.hasPermission("announcestream.cmd.stream")){
                if(args.length == 0){
                    String message = config.getString("options.messages.command.stream_usage");
                    message = message.replace("<prefix>", prefix);
                    player.sendMessage(Msg.color(message));
                    return true;
                }

                if(args[0].startsWith("https://")) {
                    if (as.getCooldowns().hasCooldown(player.getUniqueId().toString())) {
                        String message = config.getString("options.messages.command.cooldown");
                        message = message.replace("<time>", as.getCooldowns().getCooldown(player.getUniqueId().toString()) + "");
                        if (config.getBoolean("options.sounds.reproduce")) execute1(player);
                        player.sendMessage(Msg.color(message));
                        return true;
                    }
                    as.getCooldowns().setCooldown(player.getUniqueId().toString());
                    String text = config.getString("options.messages.command.motd_stream");
                    String[] textSplit = text.split("\n");
                    for (int i = 0; i < textSplit.length; i++) {
                        String motd = textSplit[i];
                        motd = StringUtil.setPlaceholders(motd, player);
                        motd = PlaceholderAPI.setPlaceholders(player, motd);
                        motd = motd.replace("<link>", args[0]);
                        motd = Msg.color(motd);
                        for(Player all : Bukkit.getOnlinePlayers()){
                            all.sendMessage(motd);
                        }
                    }

                    if (config.getBoolean("options.sounds.reproduce")) execute2(player);
                    if (config.getBoolean("options.use_component")) {
                        TextComponent component = new TextComponent(Msg.color(config.getString("options.messages.command.hover_name")));
                        component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, args[0]));
                        String hoverText = config.getString("options.messages.command.hover_text");
                        hoverText = StringUtil.setPlaceholders(hoverText, player);
                        hoverText = PlaceholderAPI.setPlaceholders(player, hoverText);
                        hoverText = Msg.color(hoverText);
                        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).create()));
                        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                            all.spigot().sendMessage(component);
                        }
                    }
                    return true;
                }

                player.sendMessage(Msg.color(config.getString("options.messages.command.invalid_format")));
                return false;

            } else {
                String message = config.getString("options.messages.not_permission");
                message = message.replace("<prefix>", prefix);
                if (config.getBoolean("options.sounds.reproduce")) execute(player);
                player.sendMessage(Msg.color(message));
                return false;
            }
        } else {
            String message = config.getString("options.messages.not_console");
            message = message.replace("<prefix>", prefix);
            log.sendMessage(Msg.color(message));
            return false;
        }
    }

    private void execute(Player player){ player.playSound(player.getLocation(), getSound(), getVolume(), getPitch()); }

    private void execute1(Player player){ player.playSound(player.getLocation(), getSound1(), getVolume(), getPitch()); }

    private void execute2(Player player){ player.playSound(player.getLocation(), getSound2(), getVolume(), getPitch()); }
}
