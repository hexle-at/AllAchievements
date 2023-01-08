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
import java.util.Objects;

public class AllAchievements extends JavaPlugin implements Listener {

    private List<Advancement> advancementList;
    private List<Advancement> finishedAdvancementList;

    public static boolean timer = false;
    public static int timerseconds = 0;

    @Override
    public void onEnable(){

        advancementList = new ArrayList<>();
        finishedAdvancementList = new ArrayList<>();

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
        config.set("timer", timerseconds);
        saveConfig();
    }

    public void init(){
        Iterator<Advancement> advancementIterator = Bukkit.getServer().advancementIterator();
        while(advancementIterator.hasNext()){
            Advancement a = advancementIterator.next();
            try {
                if (Objects.requireNonNull(a.getDisplay()).shouldAnnounceChat()) {
                    advancementList.add(a);
                }
            }catch (Exception e){}
        }
    }



    public List<String> getFinishedAchievements(){
        List<String> finishedStrings = new ArrayList<>();
        for(Advancement advancement : finishedAdvancementList){
            finishedStrings.add(advancement.getDisplay().getTitle());
        }
        return finishedStrings;
    }

    public List<String> getAllAchievemnts(){
        List<String> allStrings = new ArrayList<>();
        for(Advancement advancement : advancementList){
            allStrings.add(advancement.getDisplay().getTitle());
        }
        return allStrings;
    }

    public void start(){
        //start timer and listener
        timer = true;
    }

    public void stop(){
        //stop timer and listener
        timer = false;
    }

    public void pause(){
        //pause timer and listener
        if(timer){
            timer = false;
        }else{
            timer = true;
        }
    }

    public void reset(){
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

        //fromat string to 00:00:00
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        return "§6" + time;
    }

}
