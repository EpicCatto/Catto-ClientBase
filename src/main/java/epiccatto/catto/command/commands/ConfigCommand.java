package epiccatto.catto.command.commands;

import epiccatto.catto.Client;
import epiccatto.catto.command.Command;
import epiccatto.catto.module.file.config.Config;
import epiccatto.catto.utils.ChatUtil;
import net.minecraft.util.EnumChatFormatting;

public final class ConfigCommand implements Command {

    @Override
    public boolean run(String[] args) {
        if (args.length >= 2) {
            String upperCaseFunction = args[1].toUpperCase();

            if (args.length == 3) {
                switch (upperCaseFunction) {
                    case "LOAD":
                        if (Client.instance.configManager.loadConfig(args[2]))
                        	ChatUtil.sendChatMessageWPrefix("Successfully loaded config: '" + args[2] + "'");
                        else
                        	ChatUtil.sendChatMessageWPrefix("Failed to load config: '" + args[2] + "'");
                        break;
                    case "SAVE":
                        if (Client.instance.configManager.saveConfig(args[2]))
                        	ChatUtil.sendChatMessageWPrefix("Successfully saved config: '" + args[2] + "'");
                        else
                        	ChatUtil.sendChatMessageWPrefix("Failed to save config: '" + args[2] + "'");
                        break;
                    case "DELETE":
                        if (Client.instance.configManager.deleteConfig(args[2]))
                        	ChatUtil.sendChatMessageWPrefix("Successfully deleted config: '" + args[2] + "'");
                        else
                        	ChatUtil.sendChatMessageWPrefix("Failed to delete config: '" + args[2] + "'");
                        break;
                }
                return true;
            } else if (args.length == 2 && upperCaseFunction.equalsIgnoreCase("LIST")) {
            	ChatUtil.sendChatMessageWPrefix("Available Configs:");
                for (Config config : Client.instance.configManager.getContents())
                	ChatUtil.sendChatMessageWPrefix(config.getName());
                return true;
            }
        }
        return false;
    }

    @Override
    public String usage() {
        return EnumChatFormatting.WHITE + "config <load/save/delete/list> <config>";
    }
}
