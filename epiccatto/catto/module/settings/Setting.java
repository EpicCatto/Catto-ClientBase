package epiccatto.catto.module.settings;

import com.google.gson.JsonObject;
import epiccatto.catto.Client;
import epiccatto.catto.module.Module;

import java.util.function.Supplier;

public class Setting {

	public String name;
	private Module parent;
	private Supplier<Boolean> visible;

	public Setting(String name, Module parent) {
		this.name = name;
		this.parent = parent;

//		Client.instance.settingsManager.registerSetting(this);
	}

	public Object getValue() {
		return null;
	}
	public String getName() {
		return this.name;
	}

	public Module getModule() {
		return this.parent;
	}

	public boolean isVisible() {
		return visible.get();
	}

	public void setVisible(Supplier<Boolean> visibility) {
		this.visible = visibility;
	}

	public void save(JsonObject object){}
	public void load(JsonObject object){}
}