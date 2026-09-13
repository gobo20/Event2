package com.example.eventplugin;
import com.example.eventplugin.commands.EventCommand;
import com.example.eventplugin.commands.TeamCommand;
import org.bukkit.plugin.java.JavaPlugin;
public class EventPlugin extends JavaPlugin {
    private static EventPlugin instance;
    @Override public void onEnable() {
        instance = this;
        saveDefaultConfig();
        EventCommand eventCmd = new EventCommand();
        getCommand("event").setExecutor(eventCmd);
        getCommand("event").setTabCompleter(eventCmd);
        TeamCommand teamCmd = new TeamCommand();
        getCommand("blau").setExecutor(teamCmd);
        getCommand("rot").setExecutor(teamCmd);
        getCommand("blue").setExecutor(teamCmd);
        getCommand("red").setExecutor(teamCmd);
        getLogger().info("EventTeamPlugin aktiviert!");
    }
    @Override public void onDisable() {
        getLogger().info("EventTeamPlugin deaktiviert!");
    }
    public static EventPlugin getInstance() { return instance; }
}
