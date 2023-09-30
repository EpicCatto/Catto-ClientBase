package catto.uwu.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtil {
	
    public static void sendChatMessageWPrefix(final Object msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Catto: " + EnumChatFormatting.WHITE + msg.toString()));
    }
    
    public static void sendChatMessageWOutPrefix(final Object msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg.toString()));
    }
    
}
