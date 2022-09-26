package epiccatto.catto.module.modules.combat;

import epiccatto.catto.module.Category;
import epiccatto.catto.module.Module;
import net.minecraft.entity.EntityLivingBase;

public class Killaura extends Module {

    public static EntityLivingBase target;

    public Killaura() {
        super("Killaura", "Automatically attack things around you!", Category.COMBAT, 0);
    }



}
