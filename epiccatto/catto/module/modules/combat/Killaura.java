package epiccatto.catto.module.modules.combat;

import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.EventUpdate;
import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.settings.impl.NumberSetting;
import epiccatto.catto.utils.TimerUtil;
import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;

public class Killaura extends Module {

    public static EntityLivingBase target;
    private final ArrayList<EntityLivingBase> targets = new ArrayList<>();

    private final NumberSetting attackRange = new NumberSetting("Attack Range", this, 4.5, 0.1, 7, false);

    private final NumberSetting cps = new NumberSetting("CPS", this, 10, 1, 20, false);
    private final NumberSetting maxCps = new NumberSetting("Max CPS", this, 10, 1, 20, false);
    private final NumberSetting minCps = new NumberSetting("Min CPS", this, 5, 1, 20, false);



    public TimerUtil attackTimer = new TimerUtil();


    public Killaura() {
        super("Killaura", "Automatically attack things around you!", Category.COMBAT, 0);
        addSettings();
    }



    @EventTarget
    public void onUpdate(EventUpdate event){



    }

    @Override
    public void onEnable() {
        targets.clear();
        target = null;


    }
}