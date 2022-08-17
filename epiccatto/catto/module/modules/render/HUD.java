package epiccatto.catto.module.modules.render;

import epiccatto.catto.Client;
import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.Event2D;
import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;

public class HUD extends Module {
    public HUD() {
        super("HUD", "Show module on your screen cuz yes :D", Category.RENDER, 0);
    }

    @EventTarget
    public void onRender2D(Event2D event){
        mc.fontRendererObj.drawStringWithShadow(Client.clientName, 2, 2, 0xFFFFFF);
    }
}
