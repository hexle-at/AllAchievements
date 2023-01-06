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
        if(event.getAdvancement() == null || event.getAdvancement().getDisplay() == null) return;
        if(!event.getAdvancement().getDisplay().shouldAnnounceChat()) return;
        if(AllAchievements.finishedAdvancementList.contains(event.getAdvancement())) return;
        AllAchievements.finishedAdvancementList.add(event.getAdvancement());
        Bukkit.broadcastMessage("§7------- §6AllAchievements§7 ---------");
        Bukkit.broadcastMessage("§6"+AllAchievements.finishedAdvancementList.size()+"/"+AllAchievements.advancementList.size()+" achievements completed!");
        Bukkit.broadcastMessage("§7------------------------------");
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event){
        if(event.getView().getTitle().equals("§6AllAchievements")){
            event.setCancelled(true);
            int page = Integer.parseInt(event.getInventory().getItem(49).getItemMeta().getDisplayName().split(" ")[1]);
            if(event.getSlot() == 48){
                page--;
            }else if(event.getSlot() == 50){
                page++;
            }else{
                return;
            }
            Stats.showStats((Player) event.getWhoClicked(), page);
        }
    }

}
