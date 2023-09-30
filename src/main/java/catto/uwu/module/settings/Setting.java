package catto.uwu.module.settings;

import com.google.gson.JsonObject;
import catto.uwu.module.api.Module;
import catto.uwu.module.settings.impl.NoteSetting;

import java.util.function.Supplier;

public class Setting {

	public String name;
	private final Module parent;
	private Supplier<Boolean> visible;
	private NoteSetting parentNote;

	public Setting(String name, Module parent) {
		this.name = name;
		this.parent = parent;
	}
	public Setting(String name, Module parent, NoteSetting parentNote) {
		this.name = name;
		this.parent = parent;
		this.parentNote = parentNote;
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

	public NoteSetting getParentNote(){
		return this.parentNote;
	}

	public void save(JsonObject object){}
	public void load(JsonObject object){}
}