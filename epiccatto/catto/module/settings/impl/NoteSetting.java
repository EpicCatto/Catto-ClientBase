package epiccatto.catto.module.settings.impl;

import epiccatto.catto.module.api.Module;
import epiccatto.catto.module.settings.Setting;

import java.util.function.Supplier;

public class NoteSetting extends Setting  {

	private String text;

	public NoteSetting(String name, Module parent) {
		super(name, parent);
		setVisible(() -> true);
	}

	public NoteSetting(String name, Module parent, Supplier<Boolean> supplier) {
		super(name, parent);
		setVisible(supplier);
	}

	@Override
	public String getValue() {
		return text;
	}

	/*public void save(JsonObject object) {
		object.addProperty(getName(), getValue());
	}

	public void load(JsonObject object) {
		setValue(object.getAsDouble());
	}*/

}
