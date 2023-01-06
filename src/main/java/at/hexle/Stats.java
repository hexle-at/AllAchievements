package at.hexle;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Stats {

    public static void showStats(Player player, int page){
        Inventory inv = Bukkit.createInventory(null, 54, "§6AllAchievements");

        int next, last;

        Pagination<String> pagination = new Pagination<>(45, AllAchievements.getFinishedAchievements());

        if(pagination.exists(page + 1)){
            next = 0;
        }else{
            next = page+1;
        }
        if(page-1<0){
            last = pagination.totalPages();
        }else{
            last = page-1;
        }

    }

}
