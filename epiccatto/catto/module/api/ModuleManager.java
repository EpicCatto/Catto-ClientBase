package epiccatto.catto.module.api;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private static final ArrayList<Module> modules = new ArrayList<Module>();

    public void registerNormal(){
        new Reflections("epiccatto").getTypesAnnotatedWith(ModuleData.class).forEach((aClass -> {
            try {
                modules.add((Module) aClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }));
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
