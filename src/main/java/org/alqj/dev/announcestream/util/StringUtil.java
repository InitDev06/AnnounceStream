package org.alqj.dev.announcestream.util;

import org.alqj.dev.announcestream.AnnounceStream;

import org.bukkit.entity.Player;

public class StringUtil {

    private static final AnnounceStream as = AnnounceStream.getPlugin(AnnounceStream.class);

    public static String setPlaceholders(String text, Player player){
        if(player == null) return null;

        if(text.contains("<player>")) text = text.replace("<player>", player.getName());
        if(text.contains("<player_prefix>")) text = text.replace("<player_prefix>", as.chat.getPlayerPrefix(player));
        if(text.contains("<player_suffix>")) text = text.replace("<player_suffix>", as.chat.getPlayerSuffix(player));

        return text;
    }
}
