package catto.uwu.module.modules.player;

import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.EventMotion;
import catto.uwu.event.impl.EventReceivePacket;
import catto.uwu.event.impl.EventSendPacket;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.module.settings.impl.ModeSetting;
import catto.uwu.utils.ChatUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;

@ModuleData(name = "ForceOP", description = "Gives you op", category = Category.PLAYER)
public class ForceOP extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Hypixel", "Minemen", }, "NCP");

    private boolean canceling = false;

    @EventTarget
    public void onPacketReceive(EventReceivePacket event) {
        Packet packet = event.getPacket();
        if (canceling){
            if (packet.getClass().getSimpleName().startsWith("S")){
                event.setCancelled(true);
            }
        }
    }

    @EventTarget
    public void onPacketSend(EventSendPacket event) {
        Packet packet = event.getPacket();
//        Chat Packet
        if(packet instanceof C01PacketChatMessage){
            C01PacketChatMessage chatPacket = (C01PacketChatMessage) packet;
            String message = chatPacket.getMessage();

            if (message.startsWith("/gamemode 1") && canceling){
                //                Set player gamemode (client side)
                mc.thePlayer.capabilities.isCreativeMode = true;
                mc.thePlayer.capabilities.disableDamage = true;
                mc.thePlayer.capabilities.allowFlying = true;
                mc.thePlayer.capabilities.allowEdit = true;
                mc.playerController.setGameType(WorldSettings.GameType.CREATIVE);

                ChatUtil.sendChatMessageWOutPrefix(EnumChatFormatting.GRAY + "[" + mc.thePlayer.getName() + ": Set own game mode to Creative Mode]");

                event.setCancelled(true);
            }

            if (message.startsWith("/gamemode 0") && canceling){
                //                Set player gamemode (client side)
                mc.thePlayer.capabilities.isCreativeMode = false;
                mc.thePlayer.capabilities.disableDamage = false;
                mc.thePlayer.capabilities.allowFlying = false;
                mc.thePlayer.capabilities.allowEdit = false;
                mc.playerController.setGameType(WorldSettings.GameType.SURVIVAL);

                ChatUtil.sendChatMessageWOutPrefix(EnumChatFormatting.GRAY + "[" + mc.thePlayer.getName() + ": Set own game mode to Survival Mode]");

                event.setCancelled(true);
            }

            if(message.startsWith("/forceop")){
                canceling = true;
                new Thread(() -> {
                    try {
                        ChatUtil.sendChatMessageWPrefix("Oping... please wait");
                        Thread.sleep(1000);
                        ChatUtil.sendChatMessageWPrefix("Sending packet...");
                        Thread.sleep(700);

                        ChatUtil.sendChatMessageWOutPrefix(EnumChatFormatting.GRAY + "[Server: Made " + mc.thePlayer.getName() + " a server operator]");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                event.setCancelled(true);
            }
        }

        if (canceling){
//            Cancel outgoing packets
//            get if packet clss starts with "s"
            if ((packet instanceof C03PacketPlayer || packet instanceof C13PacketPlayerAbilities)){
                if (mc.thePlayer.ticksExisted < 20) return;
                event.setCancelled(true);
//                ChatUtil.sendChatMessageWOutPrefix(EnumChatFormatting.GRAY + "[" + mc.thePlayer.getName() + ": Cancelled packet " + packet.getClass().getSimpleName() + "]");
            }

        }

    }




    @Override
    public void onDisable() {
        canceling = false;
        mc.thePlayer.capabilities.isCreativeMode = false;
        mc.thePlayer.capabilities.disableDamage = false;
        mc.thePlayer.capabilities.allowFlying = false;
        mc.thePlayer.capabilities.allowEdit = false;
        mc.playerController.setGameType(WorldSettings.GameType.SURVIVAL);

        mc.timer.timerSpeed = 1f;
        super.onDisable();
    }




}
