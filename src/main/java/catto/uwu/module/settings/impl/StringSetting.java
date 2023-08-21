package catto.uwu.module.settings.impl;

import com.google.gson.JsonObject;
import catto.uwu.module.api.Module;
import catto.uwu.module.settings.Setting;

import java.util.function.Supplier;

public class StringSetting extends Setting {

	private String value;

	public StringSetting(String name, Module parent, String defaultValue) {
		super(name, parent);
		this.value = defaultValue;
		setVisible(() -> true);
	}

	public StringSetting(String name, Module parent, String defaultValue, Supplier<Boolean> supplier) {
		super(name, parent);
		this.value = defaultValue;
		setVisible(supplier);
	}

	public StringSetting(String name, Module parent, String defaultValue, NoteSetting parentNote) {
		super(name, parent, parentNote);
		this.value = defaultValue;
		setVisible(() -> true);
	}

	public StringSetting(String name, Module parent, String defaultValue, Supplier<Boolean> supplier, NoteSetting parentNote) {
		super(name, parent, parentNote);
		this.value = defaultValue;
		setVisible(supplier);
	}

	@Override
	public String getValue() {
		return this.value;
	}
 	
 	public void setValue(String value) {
 		this.value = value;
 	}

	public void save(JsonObject object) {
		object.addProperty(getName(), getValue());
	}

	public void load(JsonObject object) {
		setValue(object.get(getName()).getAsString());
	}
}
