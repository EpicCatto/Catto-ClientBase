package catto.uwu.ui.hud.elements;

import catto.uwu.Client;
import catto.uwu.ui.hud.Element;
import net.minecraft.client.Minecraft;

public class Label extends Element {
    public String text;


    public Label(String name, String text) {
        super(name, 0, 0, 0, 0);
        this.text = text;
    }

    @Override
    public void draw() {
        String displayedText = text
                .replaceAll("%fps%", Integer.toString(Minecraft.getDebugFPS()))
                .replaceAll("%tps%", Double.toString(Math.round(Minecraft.getMinecraft().timer.timerSpeed * 100) / 100.0))
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
                .replaceAll("%ign%", Minecraft.getMinecraft().thePlayer.getGameProfile().getName());

        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(displayedText, x, y, -1);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(displayedText.substring(0, 1), x, y, 0xFF00FF00);
        this.width = fr.getStringWidth(displayedText);
        this.height = fr.FONT_HEIGHT;


    }
}
