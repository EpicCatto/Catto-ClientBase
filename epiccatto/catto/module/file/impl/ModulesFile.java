package epiccatto.catto.module.file.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import epiccatto.catto.module.api.Module;
import epiccatto.catto.module.api.ModuleManager;
import epiccatto.catto.module.file.IFile;

import java.io.File;

public class ModulesFile implements IFile {

    private File file;

    @Override
    public void save(Gson gson) {
        JsonObject object = new JsonObject();

        JsonObject modulesObject = new JsonObject();

        for (Module module : ModuleManager.getModules())
            modulesObject.add(module.getName(), module.save());

        object.add("Modules", modulesObject);

        writeFile(gson.toJson(object), file);
    }

    @Override
    public void load(Gson gson) {
        if (!file.exists()) {
            return;
        }

        JsonObject object = gson.fromJson(readFile(file), JsonObject.class);
        if (object.has("Modules")){
            JsonObject modulesObject = object.getAsJsonObject("Modules");

            for (Module module : ModuleManager.getModules()) {
                if (modulesObject.has(module.getName()))
                    module.load(modulesObject.getAsJsonObject(module.getName()), true);
            }
        }
    }

    @Override
    public void setFile(File root) {
        file = new File(root, "/modules.catto");
    }
}
