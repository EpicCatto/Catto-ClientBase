package epiccatto.catto.processor.impl;

import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.EventReceivePacket;
import epiccatto.catto.event.impl.EventSendPacket;
import epiccatto.catto.event.impl.EventUpdate;
import epiccatto.catto.processor.Processor;
import epiccatto.catto.ui.player.Rotation;
import epiccatto.catto.utils.ChatUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
public class RotationProcessor implements Processor {

    private final Rotation clientRotation = new Rotation();
    private final Rotation serverRotation = new Rotation();

    private boolean enabled;

    @EventTarget
    public void onPacketSend(EventSendPacket event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
            packet.yaw = serverRotation.getYaw();
            packet.pitch = serverRotation.getPitch();

//            ChatUtil.sendChatMessageWPrefix("Packet: " + packet.yaw + " " + packet.pitch);
        }
    }

    @EventTarget
    public void onPacketReceive(EventReceivePacket event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
            serverRotation.setYaw(packet.getYaw());
            serverRotation.setPitch(packet.getPitch());
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event){
        if (!event.isPre()) return;
        clientRotation.setYaw(mc.thePlayer.rotationYaw);
        clientRotation.setPitch(mc.thePlayer.rotationPitch);
    }


    public Rotation getClientRotation() {
        return clientRotation;
    }

    public Rotation getServerRotation() {
        return serverRotation;
    }
}
