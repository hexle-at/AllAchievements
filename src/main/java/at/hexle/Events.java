package at.hexle;

import at.hexle.api.AdvancementInfo;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import java.util.Iterator;

public class Events implements Listener {

    @EventHandler
    public void onAchievement(PlayerAdvancementDoneEvent event){
        if(AllAchievements.getInstance().getVersion().startsWith("v1_19")) {
            if (event.getAdvancement() == null || event.getAdvancement().getDisplay() == null) return;
            if (!event.getAdvancement().getDisplay().shouldAnnounceChat()) return;
        }else{
            if(event.getAdvancement() == null) return;
            Advancement adv = Bukkit.getAdvancement(event.getAdvancement().getKey());
            AdvancementInfo info = new AdvancementInfo(adv);
            if(info == null || !info.announceToChat()) return;
        }

        if (AllAchievements.getInstance().getFinishedAdvancementList().contains(event.getAdvancement())) return;
        AllAchievements.getInstance().getFinishedAdvancementList().add(event.getAdvancement());
        Bukkit.broadcastMessage("§7------- §6AllAchievements§7 ---------");
        Bukkit.broadcastMessage("§6" + AllAchievements.getInstance().getFinishedAdvancementList().size() + "/" + AllAchievements.getInstance().getAdvancementList().size() + " achievements completed!");
        Bukkit.broadcastMessage("§7------------------------------");
        if (AllAchievements.getInstance().getFinishedAdvancementList().size() == AllAchievements.getInstance().getAdvancementList().size()) {
            Bukkit.broadcastMessage("§7------- §6AllAchievements§7 ---------");
            Bukkit.broadcastMessage("§aAll achievements completed!");
            Bukkit.broadcastMessage("§7------------------------------");
            Bukkit.broadcastMessage(" ");
            Bukkit.broadcastMessage("§6Want to play again? Type §a/av restart §6(There will be a new world and the server will be restarted.)");
            Bukkit.broadcastMessage(" ");
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event){
        if(event.getView().getTitle().equals("§6AllAchievements")){
            event.setCancelled(true);
            int page = Integer.parseInt(event.getInventory().getItem(49).getItemMeta().getDisplayName().split(" ")[1]);
            System.out.println(page);
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


    @EventHandler
    public void onPreJoin(PlayerPreLoginEvent event){
        if(AllAchievements.getInstance().isRestartTriggered()){
            event.setResult(PlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage("§cThe challenge is restarting ... Please try again in a few seconds!");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(AllAchievements.getInstance().getResetPlayers().contains(event.getPlayer().getUniqueId().toString())){
            Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();
            while (iterator.hasNext()){
                AdvancementProgress progress = event.getPlayer().getAdvancementProgress(iterator.next());
                for (String criteria : progress.getAwardedCriteria()) progress.revokeCriteria(criteria);
            }
            AllAchievements.getInstance().getResetPlayers().remove(event.getPlayer().getUniqueId().toString());
        }
    }

}
