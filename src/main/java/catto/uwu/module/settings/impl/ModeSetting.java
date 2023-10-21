package catto.uwu.module.settings.impl;

import catto.uwu.module.settings.Setting;
import com.google.gson.JsonObject;
import catto.uwu.module.api.Module;

import java.util.function.Supplier;

public class ModeSetting extends Setting {

	private String value;
	private final String[] options;
	
	public ModeSetting(String name, Module parent, String[] options, String defaultValue) {
		super(name, parent);
		this.options = options;
		this.value = defaultValue;
		setVisible(() -> true);
	}

	public ModeSetting(String name, Module parent, String[] options, String defaultValue, Supplier<Boolean> supplier) {
		super(name, parent);
		this.options = options;
		this.value = defaultValue;
		setVisible(supplier);
	}

	public ModeSetting(String name, Module parent, String[] options, String defaultValue, NoteSetting parentNote) {
		super(name, parent, parentNote);
		this.options = options;
		this.value = defaultValue;
		setVisible(() -> true);
	}

	public ModeSetting(String name, Module parent, String[] options, String defaultValue, Supplier<Boolean> supplier, NoteSetting parentNote) {
		super(name, parent, parentNote);
		this.options = options;
		this.value = defaultValue;
		setVisible(supplier);
	}

	public void cycle() {
		for (int i = 0; i < options.length; i++) {
			if (options[i].equals(value)) {
				if (i + 1 < options.length) {
					value = options[i + 1];
				} else {
					value = options[0];
				}
				break;
			}
		}
	}

	public String[] getOptions() {
		return this.options;
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
