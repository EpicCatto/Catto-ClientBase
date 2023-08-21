package catto.uwu.command.commands;

import catto.uwu.command.Command;
import catto.uwu.utils.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;

public class HClipCommand implements Command {

    public Minecraft mc = Minecraft.getMinecraft();

    @Override
    public boolean run(String[] args) {
        if (args.length == 2) {
            try {

                double posMod = Double.parseDouble(args[1]);
                if (mc.thePlayer.getHorizontalFacing() == EnumFacing.SOUTH) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ + posMod);
                }
                if (mc.thePlayer.getHorizontalFacing() == EnumFacing.WEST) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX + -posMod, mc.thePlayer.posY, mc.thePlayer.posZ);
                }
                if (mc.thePlayer.getHorizontalFacing() == EnumFacing.EAST) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX + posMod, mc.thePlayer.posY, mc.thePlayer.posZ);
                }
                if (mc.thePlayer.getHorizontalFacing() == EnumFacing.NORTH) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ + -posMod);
                }
                ChatUtil.sendChatMessageWPrefix("Teleported " + posMod + " blocks horizontally.");
                return true;
            }catch (Exception e){
                ChatUtil.sendChatMessageWOutPrefix(EnumChatFormatting.RED + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public String usage() {
        return EnumChatFormatting.WHITE + "hclip <value>";
    }
}