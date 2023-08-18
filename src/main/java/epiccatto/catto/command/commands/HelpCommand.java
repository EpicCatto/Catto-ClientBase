package epiccatto.catto.command.commands;

import epiccatto.catto.Client;
import epiccatto.catto.command.Command;
import epiccatto.catto.utils.ChatUtil;
import net.minecraft.util.EnumChatFormatting;


public class HelpCommand implements Command {

	@Override
	public boolean run(String[] args) {
		ChatUtil.sendChatMessageWPrefix("\u00A7b\u00A7lList of commands:");
		for (Command command : Client.instance.commandManager.getCommands().values())
			ChatUtil.sendChatMessageWPrefix(command.usage());
		return true;
	}

	@Override
	public String usage() {
		return EnumChatFormatting.WHITE + "help <lists commands>";
	}

}
