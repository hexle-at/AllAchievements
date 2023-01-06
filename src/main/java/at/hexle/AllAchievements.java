package at.hexle;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllAchievements extends JavaPlugin implements Listener {

    public static List<Advancement> advancementList = new ArrayList<>();
    public static List<Advancement> finishedAdvancementList = new ArrayList<>();

    public static boolean timer = false;
    public static int timerseconds = 0;

    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        timerseconds = this.getConfig().getInt("timer");
        Bukkit.getConsoleSender().sendMessage("------------------------------------------------------");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("._   _   _____  __  __  _       _____");
        Bukkit.getConsoleSender().sendMessage("| | | | | ____| \\ \\/ / | |     | ____|                         ");
        Bukkit.getConsoleSender().sendMessage("| |_| | |  _|    \\  /  | |     |  _|                           ");
        Bukkit.getConsoleSender().sendMessage("|  _  | | |___   /  \\  | |___  | |___");
        Bukkit.getConsoleSender().sendMessage("|_| |_| |_____| /_/\\_\\ |_____| |_____|");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("Hexle_Development_Systems - https://hexle.at");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("------------------------------------------------------");

        this.getCommand("av").setExecutor(new Commands());
        this.getCommand("av").setTabCompleter(new TabCompleter());

        Bukkit.getPluginManager().registerEvents(new Events(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if(timer && Bukkit.getOnlinePlayers().size() >= 2){
                    timerseconds++;
                }
                if(timerseconds > 0){
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(getTime()));
                    }
                }else{
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6--:--"));
                    }
                }
            }
        }, 0L, 20L);

        init();
    }

    @Override
    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage("Plugin shutdown...");
        FileConfiguration config = this.getConfig();
        config.set("advancements", finishedAdvancementList);
        saveConfig();
    }

    public static void init(){
        Iterator<Advancement> advancementIterator = Bukkit.getServer().advancementIterator();
        while(advancementIterator.hasNext()){
            Advancement a = advancementIterator.next();
            advancementList.add(a);
        }
    }



    public static List<String> getFinishedAchievements(){
        List<String> finishedStrings = new ArrayList<>();
        for(Advancement advancement : finishedAdvancementList){
            finishedStrings.add(advancement.getDisplay().getTitle());
        }
        return finishedStrings;
    }

    public static void start(){
        //start timer and listerner
        timer = true;
    }

    public static void stop(){
        //stop timer and listerner
        timer = false;
    }

    public static void pause(){
        //pause timer and listerner
        if(timer){
            timer = false;
        }else{
            timer = true;
        }
    }

    public static void reset(){
        timer = false;
        timerseconds = 0;
        finishedAdvancementList.clear();
    }

    public AllAchievements getInstance(){
        return this;
    }

    public static String getTime(){
        //format to Timestring timerseconds
        int hours = timerseconds / 3600;
        int remainder = timerseconds % 3600;
        int minutes = remainder / 60;
        int seconds = remainder % 60;
        return "§6" + hours + ":" + minutes + ":" + seconds;
    }

}
