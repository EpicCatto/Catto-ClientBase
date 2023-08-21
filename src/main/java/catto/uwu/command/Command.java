package catto.uwu.command;

public interface Command {

	boolean run(String[] args);

	String usage();

}
