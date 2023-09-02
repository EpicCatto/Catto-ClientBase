package catto.uwu.module.api;

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    RENDER("Render"),
    WORLD("World"),
    CONFIG("Config");

	public final String name;
	public boolean hidden;
	public int x;
	public int y;

    Category(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}