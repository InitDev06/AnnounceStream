package org.alqj.dev.announcestream.controllers;

import org.alqj.dev.announcestream.AnnounceStream;
import org.alqj.dev.announcestream.color.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class Versions {

    private final AnnounceStream as;
    private final ConsoleCommandSender log;

    public Versions(AnnounceStream as){
        this.as = as;
        this.log = Bukkit.getConsoleSender();
        setupVersion();
    }

    private void setupVersion(){
        try{
            String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

            switch(version){
                case "v1_8_R3":
                    log.sendMessage(Msg.color("&f Minecraft: &b1.8_R3"));
                    return;
                case "v1_9_R2":
                    log.sendMessage(Msg.color("&f Minecraft: &b1.9_R2"));
                    return;
                case "v1_10_R1":
                    log.sendMessage(Msg.color("&f Minecraft: &b1.10_R1"));
                    return;
                case "v1_11_R1":
                    log.sendMessage(Msg.color("&f Minecraft: &b1.11_R1"));
                    return;
                case "v1_12_R1":
                    log.sendMessage(Msg.color("&f Minecraft: &b1.12_R1"));
                    return;
                case "v1_13_R2":
                    log.sendMessage(Msg.color("&f Minecraft: &b1.13_R2"));
                    return;
                case "v1_14_R1":
                    log.sendMessage(Msg.color("&f Minecraft: &b1.14_R1"));
                    return;
                case "v1_15_R1":
                    log.sendMessage(Msg.color("&f Minecraft: &b1.15_R1"));
                    return;
                case "v1_16_R3":
                    log.sendMessage(Msg.color("&f Minecraft: &b1.16_R3"));
                    return;
                case "v1_17_R1":
                    log.sendMessage(Msg.color("&f Minecraft: &b1.17_R1"));
                    return;
                case "v1_18_R1":
                    log.sendMessage(Msg.color("&f Minecraft: &b1.18_R1"));
                    return;
            }
            log.sendMessage(Msg.color("&c Your server version is invalid, you're using an unsupported version?"));
            log.sendMessage(Msg.color("&c The plugin will be deactivated..."));
            Bukkit.getPluginManager().disablePlugin(as);
        } catch(ArrayIndexOutOfBoundsException ex){
            log.sendMessage(Msg.color("&c An occurred a error to check your server version"));
            log.sendMessage(Msg.color("&c The plugin will be deactivated..."));
            Bukkit.getPluginManager().disablePlugin(as);
        }
    }
}
