package epiccatto.catto.module.settings.impl;

import com.google.gson.JsonObject;
import epiccatto.catto.module.Module;
import epiccatto.catto.module.settings.Setting;

import java.util.function.Supplier;

public class ModeSetting extends Setting {

	private String value;
	private String[] options;
	
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
