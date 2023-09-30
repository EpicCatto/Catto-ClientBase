package catto.uwu.command.commands;

import catto.uwu.command.Command;
import catto.uwu.utils.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public class VClipCommand implements Command {

    public Minecraft mc = Minecraft.getMinecraft();

    @Override
    public boolean run(String[] args) {
        if (args.length == 2) {
            try {
                double blocks = Double.parseDouble(args[1]);
                mc.thePlayer.setEntityBoundingBox(mc.thePlayer.getEntityBoundingBox().offset(0.0D, blocks, 0.0D));
                ChatUtil.sendChatMessageWPrefix("Teleported " + blocks + " blocks.");
                return true;
            }catch (Exception e){
                ChatUtil.sendChatMessageWOutPrefix(EnumChatFormatting.RED + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public String usage() {
        return EnumChatFormatting.WHITE + "vclip <value>";
    }
}