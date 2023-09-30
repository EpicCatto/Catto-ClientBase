package catto.uwu.module.file.config;

import catto.uwu.module.settings.Serializable;
import com.google.gson.JsonObject;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public final class Config implements Serializable {

    private String name;
    private String description;
    private final File file;

    public Config(String name) {
        this.name = name;
        this.description = "Local";
        this.file = new File(ConfigManager.CONFIGS_DIR, name + ConfigManager.EXTENTION);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
    }
    public Config(String name, String description, boolean create) {
        this.name = name;
        this.description = description;
        this.file = new File(ConfigManager.CONFIGS_DIR, name + ConfigManager.EXTENTION);

        if (!file.exists() && create) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public JsonObject save() {
        JsonObject jsonObject = new JsonObject();
        JsonObject modulesObject = new JsonObject();

        jsonObject.addProperty("cfgname", name);
        jsonObject.addProperty("cfgdescription", description);
        jsonObject.addProperty("date", LocalDateTime.now().toLocalDate().toString());

        for (Module module : ModuleManager.getModules())
            modulesObject.add(module.getName(), module.save());

        jsonObject.add("Modules", modulesObject);

        return jsonObject;
    }

    @Override
    public void load(JsonObject object, boolean loadConfig) {

        setName(object.get("cfgname").getAsString());
        setDescription(object.get("cfgdescription").getAsString());

        if (object.has("Modules")) {
            JsonObject modulesObject = object.getAsJsonObject("Modules");

            for (Module module : ModuleManager.getModules()) {
                if (modulesObject.has(module.getName()))
                    module.load(modulesObject.getAsJsonObject(module.getName()), loadConfig);
            }
        }
    }
}
