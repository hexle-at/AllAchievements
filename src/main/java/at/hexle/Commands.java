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
            if(!sender.hasPermission("av.timer.start")) {
                noPerm(sender);
                return false;
            }
            AllAchievements.getInstance().start();
            Bukkit.broadcastMessage("§7-------- §6AllAchievements§7 ----------");
            Bukkit.broadcastMessage("§aThe challenge has been started!");
            Bukkit.broadcastMessage("§7--------------------------------");
        } else if (args[0].equalsIgnoreCase("pause")){
            if(!sender.hasPermission("av.timer.pause")) {
                noPerm(sender);
                return false;
            }
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
            if(!sender.hasPermission("av.timer.reset")) {
                noPerm(sender);
                return false;
            }
            Bukkit.broadcastMessage("§7-------- §6AllAchievements§7 ----------");
            Bukkit.broadcastMessage("§cThe challenge has been reset!");
            Bukkit.broadcastMessage("§7--------------------------------");
            AllAchievements.getInstance().reset();
        } else if (args[0].equalsIgnoreCase("stats")){
            if(!sender.hasPermission("av.stats")) {
                noPerm(sender);
                return false;
            }
            Stats.showStats((Player) sender, 0);
        } else {
            sendHelp(sender);
        }
        return true;
    }

    public static void noPerm(CommandSender sender){
        sender.sendMessage("§cYou don't have permission to execute this command!");
    }

    public static void sendHelp(CommandSender sender){
        sender.sendMessage("§6AllAchievements §7- §eCommands");
        sender.sendMessage("§6/av start §7- §eStarts the challange");
        sender.sendMessage("§6/av pause §7- §ePauses the challange");
        sender.sendMessage("§6/av reset §7- §eResets the challange");
        sender.sendMessage("§6/av stats §7- §eShows all challange");
    }

}
