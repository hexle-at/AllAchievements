package at.hexle;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
        } else if (args[0].equalsIgnoreCase("start")){
            AllAchievements.getInstance().start();
            Bukkit.broadcastMessage("§7-------- §6AllAchievements§7 ----------");
            Bukkit.broadcastMessage("§aThe challenge has been started!");
            Bukkit.broadcastMessage("§7--------------------------------");
        } else if (args[0].equalsIgnoreCase("pause")){
            if(AllAchievements.getInstance().isRunning()){
                Bukkit.broadcastMessage("§7-------- §6AllAchievements§7 ----------");
                Bukkit.broadcastMessage("§cThe challenge has been paused!");
                Bukkit.broadcastMessage("§7--------------------------------");
            } else {
                Bukkit.broadcastMessage("§7-------- §6AllAchievements§7 ----------");
                Bukkit.broadcastMessage("§6The challenge has been resumed!");
                Bukkit.broadcastMessage("§7--------------------------------");
            }
            AllAchievements.getInstance().pause();
        } else if (args[0].equalsIgnoreCase("reset")) {
            Bukkit.broadcastMessage("§7-------- §6AllAchievements§7 ----------");
            Bukkit.broadcastMessage("§cThe challenge has been reset!");
            Bukkit.broadcastMessage("§7--------------------------------");
            AllAchievements.getInstance().reset();
        } else if (args[0].equalsIgnoreCase("stats")){
            Stats.showStats((Player) sender, 0);
        } else if (args[0].equalsIgnoreCase("restart")){
            if(!AllAchievements.getInstance().isNewWorldOnRestart()){
                Bukkit.broadcastMessage("§7-------- §6AllAchievements§7 ----------");
                Bukkit.broadcastMessage("§cPlease enable this in your config: §6newWorldOnRestart: true");
                Bukkit.broadcastMessage("§7--------------------------------");
                return true;
            }
            if(AllAchievements.getInstance().isRunning()){
                sender.sendMessage("§cStop the timer before restarting! §6/av pause");
            }else{
                Bukkit.broadcastMessage("§7-------- §6AllAchievements§7 ----------");
                Bukkit.broadcastMessage("§aThe challenge is restarting ...");
                Bukkit.broadcastMessage("§7--------------------------------");
                AllAchievements.getInstance().restart();
            }
        } else {
            sendHelp(sender);
        }
        return true;
    }

    public static void sendHelp(CommandSender sender){
        sender.sendMessage("§6AllAchievements §7- §eCommands");
        sender.sendMessage("§6/av start §7- §eStarts the challange");
        sender.sendMessage("§6/av pause §7- §ePauses the challange");
        sender.sendMessage("§6/av reset §7- §eResets the challange");
        sender.sendMessage("§6/av restart §7- §eGenerates new world and resets the challange");
        sender.sendMessage("§6/av stats §7- §eShows all challange");
    }

}
