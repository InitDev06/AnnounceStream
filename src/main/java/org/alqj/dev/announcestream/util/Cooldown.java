package org.alqj.dev.announcestream.util;

import org.alqj.dev.announcestream.AnnounceStream;
import org.alqj.dev.announcestream.config.Config;

import java.util.HashMap;

public class Cooldown {

    private final AnnounceStream as;
    private final HashMap<String, Integer> cooldowns;

    public Cooldown(AnnounceStream as){
        this.as = as;
        this.cooldowns = new HashMap<>();
    }

    public void setCooldown(final String uuid){ cooldowns.put(uuid, (int) System.currentTimeMillis() / 1000); }

    public boolean hasCooldown(final String uuid){
        if(!cooldowns.containsKey(uuid)) return false;

        if(cooldowns.containsKey(uuid)){
            if(((int) System.currentTimeMillis() / 1000) - cooldowns.get(uuid) < Config.getFile().getInt("options.cooldown_cmd")) return true;
        }
        return false;
    }

    public void removeCooldown(final String uuid){ cooldowns.remove(uuid); }

    public Integer getCooldown(final String uuid){
        if(!cooldowns.containsKey(uuid)) return null;
        return Config.getFile().getInt("options.cooldown_cmd") - (((int) System.currentTimeMillis() / 1000) - cooldowns.get(uuid));
    }
}
