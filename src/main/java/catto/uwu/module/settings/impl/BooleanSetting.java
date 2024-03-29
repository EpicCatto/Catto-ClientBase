package catto.uwu.module.settings.impl;

import com.google.gson.JsonObject;

import catto.uwu.module.api.Module;
import catto.uwu.module.settings.Setting;

import java.util.function.Supplier;

public class BooleanSetting extends Setting {

	private boolean value;
	
	public BooleanSetting(String name, Module parent, boolean defaultValue) {
		super(name, parent);
		this.value = defaultValue;
		setVisible(() -> true);
	}

	public BooleanSetting(String name, Module parent, boolean defaultValue, Supplier<Boolean> supplier) {
		super(name, parent);
		this.value = defaultValue;
		setVisible(supplier);
	}

	public BooleanSetting(String name, Module parent, boolean defaultValue, NoteSetting parentNote) {
		super(name, parent, parentNote);
		this.value = defaultValue;
		setVisible(() -> true);
	}

	public BooleanSetting(String name, Module parent, boolean defaultValue, Supplier<Boolean> supplier, NoteSetting parentNote) {
		super(name, parent, parentNote);
		this.value = defaultValue;
		setVisible(supplier);
	}

	@Override
	public Boolean getValue() {
		return this.value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	public void toggle() {
		this.value = !this.value;
	}

	public void save(JsonObject object) {
		object.addProperty(getName(), getValue());
	}

	public void load(JsonObject object) {
		setValue(object.get(getName()).getAsBoolean());
	}
}
