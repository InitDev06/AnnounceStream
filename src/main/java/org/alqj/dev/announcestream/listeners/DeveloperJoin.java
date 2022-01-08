package org.alqj.dev.announcestream.listeners;

import org.alqj.dev.announcestream.AnnounceStream;
import org.alqj.dev.announcestream.color.Msg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DeveloperJoin implements Listener {

    private final AnnounceStream as;

    public DeveloperJoin(AnnounceStream as){
        this.as = as;
    }

    @EventHandler
    public void onDevJoin(PlayerJoinEvent ev){
        Player player = ev.getPlayer();

        if(player.getName().equals("iAlqjDV")){
            player.sendMessage(Msg.color(""));
            player.sendMessage(Msg.color("&a Welcome Developer! :D"));
            player.sendMessage(Msg.color("&a This server is running the plugin &e" + as.getPDFFile().getName() + "&6 " + as.getVersionId()));
            player.sendMessage(Msg.color(""));
        }
    }
}
