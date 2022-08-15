package epiccatto.catto.module.settings;

import com.google.gson.JsonObject;

public interface Serializable {

    JsonObject save();

    void load(JsonObject object, boolean loadConfig);
}
