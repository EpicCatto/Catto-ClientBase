package catto.uwu.ui.hud;

import catto.uwu.Client;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.module.file.IFile;
import catto.uwu.module.modules.render.HUD;
import catto.uwu.ui.hud.elements.Label;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;

public class ElementsFile implements IFile {

    private File file;

    private final ArrayList<Element> elements = HUD.getInstance().getElements();

    @Override
    public void save(Gson gson) {
        JsonObject object = new JsonObject();
        JsonObject elementObject = new JsonObject();
        for (Element element : elements) {
            elementObject.add(element.name, element.save());
        }
        object.add("Elements", elementObject);
        writeFile(gson.toJson(object), file);

        load(gson);
    }

    @Override
    public void load(Gson gson) {
        if (!file.exists()) {
            save(gson);
            return;
        }

        JsonObject object = gson.fromJson(readFile(file), JsonObject.class);

        if (object.has("Elements")) {
            JsonObject elementObject = object.getAsJsonObject("Elements");
            for (Element element : elements) {
                if (elementObject.has(element.name))
                    element.load(elementObject.getAsJsonObject(element.name), true);
            }
        }
    }

    @Override
    public void setFile(File root) {
        file = new File(root, "elements.json");
    }
}
