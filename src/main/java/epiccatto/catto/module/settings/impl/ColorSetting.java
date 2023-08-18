package epiccatto.catto.module.settings.impl;

import com.google.gson.JsonObject;
import epiccatto.catto.module.api.Module;
import epiccatto.catto.module.settings.Setting;

import java.awt.*;
import java.util.function.Supplier;

public class ColorSetting extends Setting  {

	private Color color;

	public ColorSetting(String name, Module parent, Color defaultColor) {
		super(name, parent);
		this.setColor(defaultColor);
		setVisible(() -> true);
	}

	public ColorSetting(String name, Module parent, Color defaultColor, Supplier<Boolean> supplier) {
		super(name, parent);
		this.setColor(defaultColor);
		setVisible(supplier);
	}

	@Override
	public Color getValue() {
		return color;
	}
	
	public int getColor() {
		return this.getValue().getRGB();
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void save(JsonObject object) {
		object.addProperty(getName(), getColor());
	}

	public void load(JsonObject object) {
		setColor(new Color(object.get(getName()).getAsInt()));
	}

}
