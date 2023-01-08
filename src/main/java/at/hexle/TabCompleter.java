package at.hexle;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    final String[] commands;

    public TabCompleter() {
        this.commands = new String[] { "start", "reset", "pause", "stats"};
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length <= 1) {
            StringUtil.copyPartialMatches(args[0], Arrays.asList(this.commands), completions);
        }
        return completions;
    }
}
