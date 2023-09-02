package catto.uwu.processor.impl;

import catto.uwu.event.EventTarget;
import catto.uwu.event.Priority;
import catto.uwu.event.impl.EventSendPacket;
import catto.uwu.processor.Processor;
import catto.uwu.viamcp.vialoadingbase.ViaLoadingBase;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class ViaMCPFix implements Processor {
    //    Fix sending packets while standing still
    @EventTarget(value = Priority.VERY_LOW)
    public void onPacketSend(EventSendPacket event) {
        if (ViaLoadingBase.getInstance().getTargetVersion().getVersion() > ProtocolVersion.v1_8.getVersion()) {
            if (event.getPacket() instanceof C03PacketPlayer) {
                C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
                if (!packet.isMoving() && !packet.getRotating()) {
                    event.setCancelled(true);
                }
            }
        }
        if (ViaLoadingBase.getInstance().getTargetVersion().getVersion() >= ProtocolVersion.v1_11.getVersion()) {
            if (event.getPacket() instanceof C08PacketPlayerBlockPlacement) {
                C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement) event.getPacket();
                packet.facingX /= 16.0F;
                packet.facingY /= 16.0F;
                packet.facingZ /= 16.0F;
                event.setPacket(packet);
            }
        }
    }

}
