package at.hexle;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class Stats {

    public static void showStats(Player player, int page){
        Inventory inv = Bukkit.createInventory(null, 54, "§6AllAchievements");

        for(int i = 0; i < 54; i++){
            if(AllAchievements.getInstance().getVersion().startsWith("v1_12")){
                inv.setItem(i, new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (byte)15));
            }else{
                inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            }
        }

        List<String> finishedAdvancements = AllAchievements.getInstance().getFinishedAchievements();
        Pagination<String> pagination = new Pagination<>(45, AllAchievements.getInstance().getAllAchievements());

        if(page < 0) page = 0;
        if(page > pagination.totalPages()-1) page = pagination.totalPages()-1;

        List<String> pag = pagination.getPage(page);

        int i = 0;
        for(String p : pag){
            if(finishedAdvancements.contains(p)){
                ItemStack is = null;
                if(AllAchievements.getInstance().getVersion().startsWith("v1_12")) {
                    is = new ItemStack(Material.EMERALD, 1);
                }else{
                    is = new ItemStack(Material.GREEN_DYE, 1);
                }
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("§6"+p);
                is.setItemMeta(im);
                inv.setItem(i, is);
            }else{
                ItemStack is = null;
                if(AllAchievements.getInstance().getVersion().startsWith("v1_12")) {
                    is = new ItemStack(Material.REDSTONE, 1);
                }else{
                    is = new ItemStack(Material.RED_DYE, 1);
                }
                ItemMeta im = is.getItemMeta();
                im.setDisplayName("§6"+p);
                is.setItemMeta(im);
                inv.setItem(i, is);
            }
            i++;
        }

        ItemStack item4 = new ItemStack(Material.ARROW, 1);
        ItemMeta im4 = item4.getItemMeta();
        im4.setDisplayName("§6Last Page");
        item4.setItemMeta(im4);
        inv.setItem(48, item4);

        ItemStack item6 = null;
        if(AllAchievements.getInstance().getVersion().startsWith("v1_12")){
            item6 = new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (byte)15);
        }else{
            item6 = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        }
        ItemMeta im6 = item6.getItemMeta();
        im6.setDisplayName("§6Page " + page);
        item6.setItemMeta(im6);
        inv.setItem(49, item6);

        ItemStack item5 = new ItemStack(Material.ARROW, 1);
        ItemMeta im5 = item5.getItemMeta();
        im5.setDisplayName("§6Next Page");
        item5.setItemMeta(im5);
        inv.setItem(50, item5);

        player.openInventory(inv);
    }
}
