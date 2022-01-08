package org.alqj.dev.announcestream.config;

import org.alqj.dev.announcestream.AnnounceStream;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private static final AnnounceStream as = AnnounceStream.getPlugin(AnnounceStream.class);

    public static File file;
    public static FileConfiguration config;

    public static void createFile(){
        file = new File("plugins/AnnounceStream", "config.yml");
        if(!file.exists()) as.saveResource("config.yml", false);
    }

    public static void loadFile(){ config = getFileInstance(); }

    public static void saveFile(){
        try{
            config.save(file);
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private static FileConfiguration getFileInstance(){ return YamlConfiguration.loadConfiguration(file); }

    public static void reload(){ config = YamlConfiguration.loadConfiguration(file); }

    public static FileConfiguration getFile(){ return config; }
}
