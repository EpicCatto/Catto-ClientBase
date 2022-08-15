package epiccatto.catto.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import epiccatto.catto.Client;
import epiccatto.catto.command.Command;
import epiccatto.catto.utils.ChatUtil;


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
		return ChatFormatting.WHITE + "help <lists commands>";
	}

}
