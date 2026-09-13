
package com.example.eventplugin.commands;
import com.example.eventplugin.EventPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import java.util.*;
public class EventCommand implements CommandExecutor, TabCompleter {
    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp() && !sender.hasPermission("event.admin")) { sender.sendMessage("§cKeine Rechte!"); return true; }
        if (args.length == 0) {
            sender.sendMessage("§7/event <Spieler> | /event set <red|blue>"); return true;
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (!(sender instanceof Player p)) { sender.sendMessage("§cNur als Spieler!"); return true; }
            if (args.length < 2) { sender.sendMessage("§c/event set <red|blue>"); return true; }
            String key = args[1].toLowerCase().startsWith("r") ? "red" : "blue";
            Location loc = p.getLocation();
            EventPlugin.getInstance().getConfig().set("locations."+key+".world", loc.getWorld().getName());
            EventPlugin.getInstance().getConfig().set("locations."+key+".x", loc.getX());
            EventPlugin.getInstance().getConfig().set("locations."+key+".y", loc.getY());
            EventPlugin.getInstance().getConfig().set("locations."+key+".z", loc.getZ());
            EventPlugin.getInstance().getConfig().set("locations."+key+".yaw", loc.getYaw());
            EventPlugin.getInstance().getConfig().set("locations."+key+".pitch", loc.getPitch());
            EventPlugin.getInstance().saveConfig();
            sender.sendMessage("§aSpawn für "+key+" gesetzt!"); return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) { sender.sendMessage("§cSpieler nicht online!"); return true; }
        TeamCommand.addInvited(target);
        target.sendMessage("");
        target.sendMessage("§8§l[§9Event§8] §fDu wurdest eingeladen!");
        target.sendMessage("§7Schreibe §9/Blau §7für §9Blaues Team");
        target.sendMessage("§7Schreibe §c/Rot §7für §cRotes Team");
        target.sendMessage("");
        sender.sendMessage("§a"+target.getName()+" eingeladen.");
        return true;
    }
    @Override public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> l = new ArrayList<>();
        if (args.length==1) { l.add("set"); for(Player p:Bukkit.getOnlinePlayers()) l.add(p.getName()); }
        else if (args.length==2 && args[0].equalsIgnoreCase("set")) { l.addAll(Arrays.asList("red","blue")); }
        return l;
    }
}
