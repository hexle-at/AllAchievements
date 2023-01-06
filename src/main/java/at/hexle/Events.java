package at.hexle;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Events implements Listener {

    @EventHandler
    public void onAchievement(PlayerAdvancementDoneEvent event){
        AllAchievements.finishedAdvancementList.add(event.getAdvancement());
        Bukkit.broadcastMessage("§7------- §6AllAchievements§7 ---------");
        Bukkit.broadcastMessage("§6"+AllAchievements.finishedAdvancementList.size()+"/"+AllAchievements.advancementList.size()+" achievements completed!");
        Bukkit.broadcastMessage("§7------------------------------");
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event){
        if(event.getView().getTitle().equals("§6AllAchievements")){
            if(event.getCurrentItem().getType() == Material.ARROW){
                ItemStack item = event.getCurrentItem();
                ItemMeta meta = item.getItemMeta();
                List<String> lore = meta.getLore();
                String[] loreArray = lore.get(0).split(" ");
                int page = Integer.parseInt(loreArray[1]);
                Stats.showStats((Player) event.getWhoClicked(), page);
            }
            event.setCancelled(true);
        }
    }

}
