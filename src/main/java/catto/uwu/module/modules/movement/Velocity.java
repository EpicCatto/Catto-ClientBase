package catto.uwu.module.modules.movement;

import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.EventReceivePacket;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.module.settings.impl.ModeSetting;
import catto.uwu.module.settings.impl.NumberSetting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

@ModuleData(name = "Velocity", description = "anti kb", category = Category.PLAYER)
public class Velocity extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", this, new String[]{"Cancel", "Percentage"}, "Cancel");
    private final NumberSetting percentY = new NumberSetting("Vertically", this, 100, 0, 100, true, () -> mode.getValue().equalsIgnoreCase("Percentage"));
    private final NumberSetting percentXZ = new NumberSetting("Horizontally", this, 0, 0, 100, true, () -> mode.getValue().equalsIgnoreCase("Percentage"));

    public Velocity() {
        addSettings(mode, percentXZ, percentY);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket e){
        setSuffix(mode.getValue());
        if (e.getPacket()instanceof S12PacketEntityVelocity){
            S12PacketEntityVelocity veloP = (S12PacketEntityVelocity) e.getPacket();
            if (veloP.getEntityID() == mc.thePlayer.getEntityId()){
                switch (mode.getValue()){
                    case "Cancel":
                        e.setCancelled(true);
                        break;
                    case "Percentage":
                        if (percentXZ.getValue() == 0 && percentY.getValue() == 0){
                            e.setCancelled(true);
                            return;
                        }
                        veloP.setMotionX((int) (veloP.getMotionX() * (percentXZ.getValue()/100))) ;
                        veloP.setMotionZ((int) (veloP.getMotionZ() * (percentXZ.getValue()/100)));
                        veloP.setMotionY((int) (veloP.getMotionY() * (percentY.getValue()/100)));
                        break;
                }

            }
        }

        if (e.getPacket() instanceof S27PacketExplosion){

        }
    }
}
