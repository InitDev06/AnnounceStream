package org.alqj.dev.announcestream.color;

import org.bukkit.ChatColor;

public class Msg {

    public static String color(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
