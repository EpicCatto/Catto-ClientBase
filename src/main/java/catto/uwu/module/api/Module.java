package catto.uwu.module.api;

import catto.uwu.module.settings.Serializable;
import catto.uwu.module.settings.Setting;
import com.google.gson.JsonObject;
import catto.uwu.event.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Module implements Serializable {

    public static Minecraft mc = Minecraft.getMinecraft();

    private ModuleData data;
    private boolean isShow = true;
    private String suffix = "";
    private int keyCode;
    private boolean enabled;
    private boolean extended;

    private final ArrayList<Setting> settings;

    public Module() {
        data = getClass().getAnnotation(ModuleData.class);

        this.settings = new ArrayList<>();
    }

    public String getName() {
        return data.name();
    }


    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(Object obj) {
        String suffix = obj.toString();
        if (suffix.isEmpty()) {
            this.suffix = suffix;
        } else {
            this.suffix = String.format("%s\u00a77", EnumChatFormatting.GRAY + suffix );
        }

    }

    public Category getCategory() {
        return data.category();
    }


    public int getKeyCode() {
        return this.keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getDescription() {
        return data.description();
    }
    

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled){
            EventManager.register(this);
            if (mc.theWorld != null)
            	this.onEnable();
        }else {
            EventManager.unregister(this);
            if (mc.theWorld != null)
            	this.onDisable();
        }
    }

    public void toggle(){
        setEnabled(!isEnabled());
    }

    public void onDisable() {
    }

    public void onEnable() {
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }


    public List<Setting> getSettings() {
    	return settings;
    }

    public final void addSettings(Setting... settings) {
        // added all settings to the list
        this.settings.addAll(Arrays.asList(settings));
    }

    @Override
    public JsonObject save() {
        JsonObject object = new JsonObject();
        object.addProperty("toggled", isEnabled());
        object.addProperty("key", getKeyCode());
        List<Setting> properties = settings;
        if (properties != null && !properties.isEmpty()) {
            JsonObject propertiesObject = new JsonObject();

            for (Setting settings : properties) {
                settings.save(propertiesObject);
            }

            object.add("Properties", propertiesObject);
        }
        return object;
    }

    @Override
    public void load(JsonObject object, boolean loadKeyBind) {
        if (object.has("toggled"))
            setEnabled(object.get("toggled").getAsBoolean());

        if (object.has("key") && loadKeyBind)
            setKeyCode(object.get("key").getAsInt());

        List<Setting> properties = settings;

        if (object.has("Properties")) {
            JsonObject propertiesObject = object.getAsJsonObject("Properties");
            for (Setting settings : properties) {
                if (propertiesObject.has(settings.getName())) {
                    settings.load(propertiesObject);
                }
            }
        }
    }

    

}