package at.hexle;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class Events implements Listener {

    @EventHandler
    public void onAchievement(PlayerAdvancementDoneEvent event){
        AllAchievements.finishedAdvancementList.add(event.getAdvancement());
        Bukkit.broadcastMessage("§7--------- §6AllAchievements§7 -----------");
        Bukkit.broadcastMessage("§6"+AllAchievements.finishedAdvancementList.size()+"/"+AllAchievements.advancementList.size()+" achievements completed!");
        Bukkit.broadcastMessage("§7-------------------------------------");
    }

}
