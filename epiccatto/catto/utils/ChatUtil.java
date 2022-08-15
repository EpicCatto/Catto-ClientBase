package epiccatto.catto.utils;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtil {
	
    public static void sendChatMessageWPrefix(final Object msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.YELLOW + "Catto: " + ChatFormatting.WHITE + msg.toString()));
    }
    
    public static void sendChatMessageWOutPrefix(final Object msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg.toString()));
    }
    
}
