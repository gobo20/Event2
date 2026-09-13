
package com.example.eventplugin.commands;
import com.example.eventplugin.EventPlugin;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import java.util.*;
public class TeamCommand implements CommandExecutor {
    private static final Set<UUID> invited = new HashSet<>();
    public static void addInvited(Player p){ invited.add(p.getUniqueId()); }
    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)){ return true; }
        String cmd = command.getName().toLowerCase();
        if(cmd.equals("blau")||cmd.equals("blue")||label.equalsIgnoreCase("blau")||label.equalsIgnoreCase("blue")) joinTeam(player,"blue");
        else joinTeam(player,"red");
        return true;
    }
    private void joinTeam(Player player, String team){
        String path="locations."+team;
        if(!EventPlugin.getInstance().getConfig().contains(path+".world")){ player.sendMessage("§cSpawn für "+team+" nicht gesetzt! /event set "+team); return; }
        String worldName=EventPlugin.getInstance().getConfig().getString(path+".world");
        World world=Bukkit.getWorld(worldName);
        if(world==null) return;
        double x=EventPlugin.getInstance().getConfig().getDouble(path+".x");
        double y=EventPlugin.getInstance().getConfig().getDouble(path+".y");
        double z=EventPlugin.getInstance().getConfig().getDouble(path+".z");
        float yaw=(float)EventPlugin.getInstance().getConfig().getDouble(path+".yaw");
        float pitch=(float)EventPlugin.getInstance().getConfig().getDouble(path+".pitch");
        player.teleport(new Location(world,x,y,z,yaw,pitch));
        player.getInventory().clear();
        Color color=team.equals("blue")?Color.fromRGB(0,0,255):Color.fromRGB(255,0,0);
        ItemStack helmet=new ItemStack(Material.LEATHER_HELMET);
        ItemStack chest=new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack legs=new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots=new ItemStack(Material.LEATHER_BOOTS);
        for(ItemStack piece:new ItemStack[]{helmet,chest,legs,boots}){
            LeatherArmorMeta meta=(LeatherArmorMeta)piece.getItemMeta();
            meta.setColor(color); meta.setUnbreakable(true); piece.setItemMeta(meta);
        }
        player.getInventory().setHelmet(helmet); player.getInventory().setChestplate(chest);
        player.getInventory().setLeggings(legs); player.getInventory().setBoots(boots);
        ItemStack sword=new ItemStack(Material.IRON_SWORD); sword.addUnsafeEnchantment(Enchantment.UNBREAKING,255);
        ItemStack bow=new ItemStack(Material.BOW); bow.addUnsafeEnchantment(Enchantment.UNBREAKING,255);
        player.getInventory().addItem(sword,bow);
        ItemStack arrows=new ItemStack(Material.ARROW,64);
        player.getInventory().addItem(arrows,arrows,arrows,arrows);
        if(team.equals("blue")) player.sendMessage("§8[§9Event§8] §9BLAUES TEAM!");
        else player.sendMessage("§8[§9Event§8] §cROTES TEAM!");
    }
}
