package epiccatto.catto.module;

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    PLAYER("Player"),
    RENDER("Render"),
    WORLD("World"),
    CONFIG("Config");

	public String name;
	public boolean hidden;
	public int x;
	public int y;

    Category(String name){
        this.name = name;
    }
}
