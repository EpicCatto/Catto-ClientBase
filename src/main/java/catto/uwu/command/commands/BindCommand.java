package catto.uwu.command.commands;

import catto.uwu.command.Command;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.utils.ChatUtil;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class BindCommand implements Command {

    @Override
    public boolean run(String[] args) {

        if (args.length == 3) {
            Module m = ModuleManager.getModuleByName(args[1]);
            if (m != null) {
                m.setKeyCode(Keyboard.getKeyIndex(args[2].toUpperCase()));
                ChatUtil.sendChatMessageWPrefix(m.getName() + " has been bound to " + Keyboard.getKeyName(m.getKeyCode()) + ".");
                return true;
            }
        } else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("clear")) {
                ModuleManager.getModules().forEach(module -> module.setKeyCode(Keyboard.KEY_NONE));
                ChatUtil.sendChatMessageWPrefix("All binds have been cleared.");
                return true;
            }
        }


        return false;
    }

    @Override
    public String usage() {
        return EnumChatFormatting.WHITE + "bind <clear> <module> <key>";
    }
}