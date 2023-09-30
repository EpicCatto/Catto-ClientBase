package catto.uwu.event.impl;

import catto.uwu.event.Event;


public final class EventMotion extends Event {
	private double x;

	private double lastX;

	private double y;

	private double lastY;

	private double z;

	private double lastZ;

	public static float yaw;

	private float lastYaw;

	public static float pitch;

	private float lastPitch;

	private boolean onGround;

	private static boolean rotatingPitch = false;

	private static boolean rotatingYaw = false;

	private Type type;

	public EventMotion(double X, double Y, double Z, float yaw, float pitch, boolean onGround, Type type) {
		this.x = X;
		this.y = Y;
		this.z = Z;
		EventMotion.yaw = yaw;
		EventMotion.pitch = pitch;
		this.onGround = onGround;
		this.type = type;
	}

	public boolean isPre() {
		return (this.type == Type.PRE);
	}

	public boolean isPost() {
		return (this.type == Type.POST);
	}


	public double getX() {
		return this.x;
	}

	public void setX(double X) {
		this.x = X;
	}

	public double getLastX() {
		return this.lastX;
	}

	public void setLastX(double lastX) {
		this.lastX = lastX;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double Y) {
		this.y = Y;
	}

	public double getLastY() {
		return this.lastY;
	}

	public void setLastY(double lastY) {
		this.lastY = lastY;
	}

	public double getZ() {
		return this.z;
	}

	public void setZ(double Z) {
		this.z = Z;
	}

	public double getLastZ() {
		return this.lastZ;
	}

	public void setLastZ(double lastZ) {
		this.lastZ = lastZ;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		EventMotion.yaw = yaw;
		rotatingYaw = true;
	}

	public float getLastYaw() {
		return this.lastYaw;
	}

	public void setLastYaw(float lastYaw) {
		this.lastYaw = lastYaw;
	}

	public static float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		EventMotion.pitch = pitch;
		rotatingPitch = true;
	}

	public float getLastPitch() {
		return this.lastPitch;
	}

	public void setLastPitch(float lastPitch) {
		this.lastPitch = lastPitch;
	}

	public boolean isOnGround() {
		return this.onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public static boolean isRotatingPitch() {
		return rotatingPitch;
	}

	public static boolean isRotatingYaw() {
		return rotatingYaw;
	}

	public static void setRotatingPitch(boolean rotatingPitch) {
		EventMotion.rotatingPitch = rotatingPitch;
	}

	public static void setRotatingYaw(boolean rotatingYaw) {
		EventMotion.rotatingYaw = rotatingYaw;
	}

	public enum Type {
		PRE, POST
	}
}
