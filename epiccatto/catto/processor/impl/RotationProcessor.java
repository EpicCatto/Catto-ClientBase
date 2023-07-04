package epiccatto.catto.processor.impl;

import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.EventReceivePacket;
import epiccatto.catto.event.impl.EventSendPacket;
import epiccatto.catto.event.impl.EventMotion;
import epiccatto.catto.processor.Processor;
import epiccatto.catto.ui.player.Rotation;
import epiccatto.catto.utils.ChatUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
public class RotationProcessor implements Processor {
    // TODO: Movefix, Rotations in general (GCD, ETC..)

    private final Rotation clientRotation = new Rotation();
    private final Rotation serverRotation = new Rotation();

    private final Rotation toRotation = new Rotation();

    private boolean enabled;

    @EventTarget
    public void onPacketSend(EventSendPacket event) {
//        if (event.getPacket() instanceof C03PacketPlayer) {
//            C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
//            packet.yaw = serverRotation.getYaw();
//            packet.pitch = serverRotation.getPitch();
//
////            ChatUtil.sendChatMessageWPrefix("Packet: " + packet.yaw + " " + packet.pitch);
//        }
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        
    }


    public Rotation getClientRotation() {
        return clientRotation;
    }

    public Rotation getServerRotation() {
        return serverRotation;
    }


    public Rotation getToRotation() {
        return toRotation;
    }
}
