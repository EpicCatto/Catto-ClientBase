package epiccatto.catto.module;

import com.google.gson.JsonObject;
import epiccatto.catto.Client;
import epiccatto.catto.event.EventManager;
import epiccatto.catto.module.settings.Serializable;
import epiccatto.catto.module.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class Module implements Serializable {

    public static Minecraft mc = Minecraft.getMinecraft();

    private String name;
    private String description;
    private String suffix;
    private Category category;
    private int keyCode;

    private boolean enabled;
    private boolean isShow = true;

    private float xAnimation, yAnimation; // Animation

    public Module(String name, String description, Category category, int keyCode) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.keyCode = keyCode;
        this.suffix = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


    public float getXAnimation() {
        return xAnimation;
    }

    public void setXAnimation(float xAnimation) {
        this.xAnimation = xAnimation;
    }

    public float getYAnimation() {
        return yAnimation;
    }

    public void setYAnimation(float yAnimation) {
        this.yAnimation = yAnimation;
    }

    @Override
    public JsonObject save() {
        JsonObject object = new JsonObject();
        object.addProperty("toggled", isEnabled());
        object.addProperty("key", getKeyCode());
        List<Setting> properties = Client.instance.settingsManager.getSettingsFromModule(this);
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

        List<Setting> properties = Client.instance.settingsManager.getSettingsFromModule(this);

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