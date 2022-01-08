package org.alqj.dev.announcestream;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.alqj.dev.announcestream.color.Msg;
import org.alqj.dev.announcestream.commands.MainCommand;
import org.alqj.dev.announcestream.commands.StreamCommand;
import org.alqj.dev.announcestream.commands.TabComplete;
import org.alqj.dev.announcestream.config.Config;
import org.alqj.dev.announcestream.controllers.Versions;
import org.alqj.dev.announcestream.listeners.DeveloperJoin;
import org.alqj.dev.announcestream.util.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class AnnounceStream extends JavaPlugin {

    PluginDescriptionFile pdffile = getDescription();
    private final ConsoleCommandSender log = Bukkit.getConsoleSender();
    private Cooldown cooldown;

    public Chat chat = null;
    public Permission permission = null;

    public String getDeveloperName() {
        String DEV = "iAlqjDV";
        return DEV;
    }

    public String getVersionId() {
        String VER = pdffile.getVersion();
        return VER;
    }

    public PluginDescriptionFile getPDFFile(){ return pdffile; }

    @Override
    public void onEnable() {
        long START = System.currentTimeMillis();

        log.sendMessage(Msg.color(""));
        log.sendMessage(Msg.color("&e AnnounceStream"));
        log.sendMessage(Msg.color(""));
        log.sendMessage(Msg.color("&f Author: &b" + getDeveloperName()));
        log.sendMessage(Msg.color("&f  Version: &a" + getVersionId()));
        log.sendMessage(Msg.color(""));

        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException ex) {
            log.sendMessage(Msg.color("&c Could not found the Spigot/Paper .jar, you're using Spigot or Paper?"));
            log.sendMessage(Msg.color("&c The plugin will be deactivated..."));
            Bukkit.getPluginManager().disablePlugin(this);
        }

        Config.createFile();
        Config.loadFile();
        setupCommands();
        getServer().getPluginManager().registerEvents(new DeveloperJoin(this), this);
        cooldown = new Cooldown(this);
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            log.sendMessage(Msg.color("&c Vault not hooked."));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        setupChatService();
        setupPermissionService();
        log.sendMessage(Msg.color("&6 Vault hooked correctly."));

        new Versions(this);

        log.sendMessage(Msg.color("&a Enabled in &e" + (System.currentTimeMillis() - START) + "ms&a."));
        log.sendMessage(Msg.color(""));
    }

    public Cooldown getCooldowns(){ return cooldown; }

    private void setupCommands(){
        getCommand("stream").setExecutor(new StreamCommand(this));
        getCommand("announcestream").setExecutor(new MainCommand(this));

        getCommand("announcestream").setTabCompleter(new TabComplete());
    }

    private boolean setupChatService() {
        RegisteredServiceProvider<Chat> rspChat = getServer().getServicesManager().getRegistration(Chat.class);
        if (rspChat == null) return false;

        chat = rspChat.getProvider();
        return chat != null;
    }

    private boolean setupPermissionService() {
        RegisteredServiceProvider<Permission> rspPermission = getServer().getServicesManager().getRegistration(Permission.class);
        if (rspPermission == null) return false;

        permission = rspPermission.getProvider();
        return permission != null;
    }
}
