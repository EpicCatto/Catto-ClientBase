package epiccatto.catto.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import epiccatto.catto.Client;
import epiccatto.catto.command.Command;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.ModuleManager;
import epiccatto.catto.utils.ChatUtil;

public class ToggleCommand implements Command {

	@Override
	public boolean run(String[] args) {
		if (args.length == 2) {
			try {
				Module m = ModuleManager.getModuleByName(args[1]);
				if (args[1].equalsIgnoreCase(m.getName()))
					m.toggle();
				ChatUtil.sendChatMessageWPrefix(
						m.getName() + (m.isEnabled() ? " has been \u00A72enabled" : " has been \u00A74disabled"));
			} catch (Exception e) {
				ChatUtil.sendChatMessageWPrefix("Module not found.");
			}
			return true;
		}
		return false;
	}

	@Override
	public String usage() {
		return ChatFormatting.WHITE + "toggle <module>";
	}
}