package catto.uwu.command.commands;

import catto.uwu.Client;
import catto.uwu.command.Command;
import catto.uwu.utils.ChatUtil;
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
