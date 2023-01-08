package at.hexle;

import at.hexle.api.AdvancementInfo;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class AllAchievements extends JavaPlugin implements Listener {

    private List<Advancement> advancementList;
    private List<Advancement> finishedAdvancementList;

    private String version = "";

    private boolean timer = false;
    private boolean newWorldOnRestart = false;
    private boolean restartTriggered = false;
    private int timerseconds = 0;
    private List<String> worlds;
    private List<String> resetPlayers; //uuid

    private static AllAchievements instance;

    @Override
    public void onEnable(){
        instance = this;
        advancementList = new ArrayList<>();
        finishedAdvancementList = new ArrayList<>();
        resetPlayers = new ArrayList<>();

        try{
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        }catch(Exception e){
            e.printStackTrace();
        }

        this.saveDefaultConfig();
        timerseconds = this.getConfig().getInt("timer");
        newWorldOnRestart = this.getConfig().getBoolean("newWorldOnRestart");
        worlds = this.getConfig().getStringList("worldList");

        List<String> list = this.getConfig().getStringList("advancements");
        for(String s : list){
            finishedAdvancementList.add(Bukkit.getAdvancement(NamespacedKey.fromString(s)));
        }

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
        if(!version.startsWith("v1_19")
                && !version.startsWith("v1_18")
                && !version.startsWith("v1_17")
                && !version.startsWith("v1_16")
                && !version.startsWith("v1_15")
                && !version.startsWith("v1_14")
                && !version.startsWith("v1_13")
                && !version.startsWith("v1_12")){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Version not compatible: found "+version+" - required: 1.12+");
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getConsoleSender().sendMessage("------------------------------------------------------");
            getPluginLoader().disablePlugin(this);
        }
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("------------------------------------------------------");

        this.getCommand("av").setExecutor(new Commands());
        this.getCommand("av").setTabCompleter(new TabCompleter());

        Bukkit.getPluginManager().registerEvents(new Events(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if(timer && Bukkit.getOnlinePlayers().size() > 0){
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
        List<String> names = new ArrayList<>();
        for(Advancement a : finishedAdvancementList){
            names.add(a.getKey().toString());
        }
        config.set("advancements", names);
        config.set("timer", timerseconds);
        saveConfig();
    }

    public void init(){
        Iterator<Advancement> advancementIterator = Bukkit.getServer().advancementIterator();
        while(advancementIterator.hasNext()){
            Advancement a = advancementIterator.next();
            try {
                if(version.startsWith("v1_19")){
                    if (Objects.requireNonNull(a.getDisplay()).shouldAnnounceChat()) {
                        advancementList.add(a);
                    }
                }else {
                    Advancement adv = Bukkit.getAdvancement(a.getKey());
                    AdvancementInfo info = new AdvancementInfo(adv);
                    if(info != null && info.announceToChat()){
                        advancementList.add(a);
                    }
                }
            }catch (Exception e){}
        }
    }



    public List<String> getFinishedAchievements(){
        List<String> finishedStrings = new ArrayList<>();
        if(version.startsWith("v1_19")){
            for(Advancement advancement : finishedAdvancementList){
                finishedStrings.add(advancement.getDisplay().getTitle());
            }
        }else{
            for(Advancement advancement : finishedAdvancementList){
                Advancement adv = Bukkit.getAdvancement(advancement.getKey());
                AdvancementInfo info = new AdvancementInfo(adv);
                finishedStrings.add(info.getTitle());
            }
        }

        return finishedStrings;
    }

    public List<String> getAllAchievements(){
        List<String> allStrings = new ArrayList<>();
        if(version.startsWith("v1_19")){
            for(Advancement advancement : advancementList){
                allStrings.add(advancement.getDisplay().getTitle());
            }
        }else{
            for(Advancement advancement : advancementList){
                Advancement adv = Bukkit.getAdvancement(advancement.getKey());
                AdvancementInfo info = new AdvancementInfo(adv);
                allStrings.add(info.getTitle());
            }
        }
        return allStrings;
    }

    public void start(){
        timer = true;
    }

    public void pause(){
        if(timer){
            timer = false;
        }else{
            timer = true;
        }
    }

    public void restart(){
        restartTriggered = true;
        reset();
        for(Player player : Bukkit.getOnlinePlayers()){
            player.kickPlayer("Challenge restarting...");
        }

        if(newWorldOnRestart && restartTriggered){
            for(String world : worlds){
                if(Bukkit.getWorld(world) == null) continue;
                System.out.println("Generating new world: "+world);
                File worldFile = Bukkit.getWorld(world).getWorldFolder();
                Bukkit.unloadWorld(world, false);
                worldFile.delete();
            }

            for(String world : worlds){
                WorldCreator wc = new WorldCreator(world);
                if(world.contains("nether")){
                    wc.environment(World.Environment.NETHER);
                }else if(world.contains("end")) {
                    wc.environment(World.Environment.THE_END);
                }else{
                    wc.environment(World.Environment.NORMAL);
                }
                wc.type(WorldType.NORMAL);
                Bukkit.createWorld(wc);
            }
        }

        restartTriggered = false;
    }

    public void reset(){
        timer = false;
        timerseconds = 0;
        finishedAdvancementList.clear();
        for(OfflinePlayer player : Bukkit.getOfflinePlayers()){
            Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();
            while (iterator.hasNext()){
                if(player.getPlayer() == null){
                    resetPlayers.add(player.getUniqueId().toString());
                    continue;
                }
                AdvancementProgress progress = player.getPlayer().getAdvancementProgress(iterator.next());
                for (String criteria : progress.getAwardedCriteria()) progress.revokeCriteria(criteria);
            }
        }
    }

    public static AllAchievements getInstance(){
        return instance;
    }

    public List<Advancement> getAdvancementList() {
        return advancementList;
    }

    public List<Advancement> getFinishedAdvancementList() {
        return finishedAdvancementList;
    }

    public String getTime(){
        int hours = timerseconds / 3600;
        int remainder = timerseconds % 3600;
        int minutes = remainder / 60;
        int seconds = remainder % 60;

        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        return "§6" + time;
    }

    public boolean isRunning(){
        return timer;
    }

    public boolean isNewWorldOnRestart() {
        return newWorldOnRestart;
    }

    public boolean isRestartTriggered() {
        return restartTriggered;
    }

    public String getVersion(){
        return version;
    }

    public List<String> getResetPlayers(){
        return resetPlayers;
    }
}
