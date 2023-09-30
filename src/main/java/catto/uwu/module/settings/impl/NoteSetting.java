package catto.uwu.module.settings.impl;

import catto.uwu.module.settings.Setting;
import com.google.gson.JsonObject;
import catto.uwu.module.api.Module;

import java.util.function.Supplier;

public class NoteSetting extends Setting {

	private String text;
	private boolean toggle;

	public NoteSetting(String name, Module parent) {
		super(name, parent);
		setVisible(() -> true);
	}

	public NoteSetting(String name, Module parent, Supplier<Boolean> supplier) {
		super(name, parent);
		setVisible(supplier);
	}

	@Override
	public Boolean getValue() {
		return this.toggle;
	}
	public void setValue(boolean value) {
		this.toggle = value;
	}

	public void save(JsonObject object) {
		object.addProperty(getName(), getValue());
	}

	public void load(JsonObject object) {
		setValue(object.get(getName()).getAsBoolean());
	}

}
