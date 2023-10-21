package catto.uwu.module.settings.impl;

import com.google.gson.JsonObject;
import catto.uwu.module.api.Module;
import catto.uwu.module.settings.Setting;

import java.util.function.Supplier;

public class NumberSetting extends Setting {

	private double value;
	private final double min;
	private final double max;
	private final boolean onlyInt;

	private final double inc;

	public NumberSetting(String name, Module parent, double defaultValue, double min, double max, boolean onlyInt) {
		super(name, parent);
		this.value = defaultValue;
		this.min = min;
		this.max = max;
		this.onlyInt = onlyInt;
		this.inc = 0.01;
		setVisible(() -> true);
	}

	public NumberSetting(String name, Module parent, double defaultValue, double min, double max, boolean onlyInt,double inc) {
		super(name, parent);
		this.value = defaultValue;
		this.min = min;
		this.max = max;
		this.onlyInt = onlyInt;
		this.inc = inc;
		setVisible(() -> true);
	}

	public NumberSetting(String name, Module parent, double defaultValue, double min, double max, boolean onlyInt, Supplier<Boolean> supplier) {
		super(name, parent);
		this.value = defaultValue;
		this.min = min;
		this.max = max;
		this.onlyInt = onlyInt;
		this.inc = 0.01;
		setVisible(supplier);
	}
	public NumberSetting(String name, Module parent, double defaultValue, double min, double max, boolean onlyInt, Supplier<Boolean> supplier,double inc) {
		super(name, parent);
		this.value = defaultValue;
		this.min = min;
		this.max = max;
		this.onlyInt = onlyInt;
		this.inc = inc;
		setVisible(supplier);
	}

	public NumberSetting(String name, Module parent, double defaultValue, double min, double max, boolean onlyInt, NoteSetting parentNote) {
		super(name, parent, parentNote);
		this.value = defaultValue;
		this.min = min;
		this.max = max;
		this.onlyInt = onlyInt;
		this.inc = 0.01;
		setVisible(() -> true);
	}

	public NumberSetting(String name, Module parent, double defaultValue, double min, double max, boolean onlyInt, Supplier<Boolean> supplier, NoteSetting parentNote) {
		super(name, parent, parentNote);
		this.value = defaultValue;
		this.min = min;
		this.max = max;
		this.onlyInt = onlyInt;
		this.inc = 0.01;
		setVisible(supplier);
	}

	@Override
	public Double getValue() {
		if (this.onlyInt) {
			this.value = (int)this.value;
		}
		return this.value;
	}

	public float getValueFloat() {
		if (this.onlyInt) {
			this.value = (int)this.value;
		}
		return (float)this.value;
	}

	public long getValueLong() {
		if (this.onlyInt) {
			this.value = (int)this.value;
		}
		return (long)this.value;
	}

	public int getValueInt() {
		return (int)this.value;
	}

	public double getInc() {
		return inc;
	}

	public double getMin() {
		return this.min;
	}

	public double getMax() {
		return this.max;
	}

	public boolean getOnlyInt() {
		return this.onlyInt;
	}

	public void setValue(double value) {
		this.value = value;
	}


	public void save(JsonObject object) {
		object.addProperty(getName(), getValue());
	}

	public void load(JsonObject object) {
		setValue(object.get(getName()).getAsDouble());
	}
}
