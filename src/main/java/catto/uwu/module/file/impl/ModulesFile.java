package catto.uwu.module.file.impl;

import catto.uwu.module.file.IFile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleManager;

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

        try{
            JsonObject object = gson.fromJson(readFile(file), JsonObject.class);
            if (object.has("Modules")){
                JsonObject modulesObject = object.getAsJsonObject("Modules");

                for (Module module : ModuleManager.getModules()) {
                    if (modulesObject.has(module.getName()))
                        module.load(modulesObject.getAsJsonObject(module.getName()), true);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setFile(File root) {
        file = new File(root, "/modules.catto");
    }
}
