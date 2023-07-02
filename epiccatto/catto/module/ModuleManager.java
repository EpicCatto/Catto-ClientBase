package epiccatto.catto.module;

import epiccatto.catto.module.modules.movement.*;
import epiccatto.catto.module.modules.render.*;
import epiccatto.catto.module.modules.world.Scaffold;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private static final ArrayList<Module> modules = new ArrayList<Module>();

    public void registerNormal(){
        //Combat

        //Movement
        modules.add(new Fly());

        //Player

        //Render
        modules.add(new HUD());
        modules.add(new ClickGui());
        modules.add(new Cape());
        modules.add(new TestColorTween());

        //World
        modules.add(new Scaffold());

        //Config
    }


    public static ArrayList<Module> getModules() {
        return modules;
    }
    
    public Module getModuleByClass(Class<? extends Module> cls) {
        for (Module m : modules) {
            if (m.getClass() != cls)
                continue;
            return m;
        }
        return null;
    }
    
    public static List<Module> getModulesByCategory(Category c) {
        List<Module> modules = new ArrayList<>();

        for (Module m : ModuleManager.modules) {
            if (m.getCategory() == c) {
                modules.add(m);
            }
        }

        return modules;
    }

    public static Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

}
