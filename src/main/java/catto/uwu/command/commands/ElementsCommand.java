package catto.uwu.command.commands;

import catto.uwu.Client;
import catto.uwu.command.Command;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.module.modules.render.HUD;
import catto.uwu.ui.hud.Element;
import catto.uwu.ui.hud.ElementsFile;
import catto.uwu.utils.ChatUtil;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class ElementsCommand implements Command {

    @Override
    public boolean run(String[] args) {
        if (args.length == 2) {
            switch (args[1].toLowerCase()){
                case "list":
                    ChatUtil.sendChatMessageWPrefix("Available Elements:");
                    for (Element element : HUD.getInstance().getElements())
                        ChatUtil.sendChatMessageWPrefix(element.name);
                    return true;
                case "load":
                    Client.instance.fileFactory.loadFile(ElementsFile.class);
                    ChatUtil.sendChatMessageWPrefix("Elements have been loaded from elements.json.");
                    return true;
                case "save":
                    Client.instance.fileFactory.saveFile(ElementsFile.class);
                    ChatUtil.sendChatMessageWPrefix("Elements have been saved to elements.json.");
                    return true;
                case "create":

                    break;
            }

        }
        return false;
    }

    @Override
    public String usage() {
        return EnumChatFormatting.WHITE + "elements <save/load/list>";
    }
}