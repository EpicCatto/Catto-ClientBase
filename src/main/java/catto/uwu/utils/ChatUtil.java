package catto.uwu.utils;

import catto.uwu.Client;
import catto.uwu.utils.render.ColorUtil;
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
    public static String formatLabel(String text){
        return ColorUtil.translateAlternateColorCodes('&', text)
                .replaceAll("%fps%", Integer.toString(Minecraft.getDebugFPS()))
                .replaceAll("%tps%", Double.toString(Math.round(Minecraft.getMinecraft().timer.timerSpeed * 100) / 100.0))
                .replaceAll("%bps%", Double.toString(Math.round((Math.hypot(Minecraft.getMinecraft().thePlayer.posX - Minecraft.getMinecraft().thePlayer.prevPosX, Minecraft.getMinecraft().thePlayer.posZ - Minecraft.getMinecraft().thePlayer.prevPosZ) * Minecraft.getMinecraft().timer.timerSpeed * 20) * 100.0) / 100.0))
                .replaceAll("%x%", Double.toString(Math.floor(Minecraft.getMinecraft().thePlayer.posX)))
                .replaceAll("%y%", Double.toString(Math.floor(Minecraft.getMinecraft().thePlayer.posY)))
                .replaceAll("%z%", Double.toString(Math.floor(Minecraft.getMinecraft().thePlayer.posZ)))
                .replaceAll("%fall%", Double.toString(Math.floor(Minecraft.getMinecraft().thePlayer.fallDistance)))
                .replaceAll("%ticks%", Integer.toString(Minecraft.getMinecraft().thePlayer.ticksExisted))
                .replaceAll("%health%", Float.toString(Math.round(Minecraft.getMinecraft().thePlayer.getHealth())))
                .replaceAll("%server%", Minecraft.getMinecraft().getCurrentServerData() == null ? "localhost" : Minecraft.getMinecraft().getCurrentServerData().serverIP)
                .replaceAll("%ping%", Minecraft.getMinecraft().getCurrentServerData() == null ? "0" : Long.toString(Minecraft.getMinecraft().getCurrentServerData().pingToServer))
                .replaceAll("%balance%", Double.toString(0))
                .replaceAll("%name%", Minecraft.getMinecraft().thePlayer.getGameProfile().getName())
                .replaceAll("%version%", Client.clientVersion)
                .replaceAll("%ign%", Minecraft.getMinecraft().thePlayer.getGameProfile().getName())
                .replaceAll("%pctime%", (java.time.LocalTime.now().getHour() > 12 ? java.time.LocalTime.now().getHour() - 12 : java.time.LocalTime.now().getHour()) + ":" + java.time.LocalTime.now().getMinute() + " " + (java.time.LocalTime.now().getHour() > 12 ? "PM" : "AM"));

    }

}
