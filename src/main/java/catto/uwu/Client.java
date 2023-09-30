package catto.uwu;

import catto.uwu.command.CommandManager;
import catto.uwu.event.EventManager;
import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.EventKey;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleManager;
import catto.uwu.module.file.FileFactory;
import catto.uwu.module.file.config.ConfigManager;
import catto.uwu.module.file.impl.ModulesFile;
import catto.uwu.ui.clickgui.dropdown.DropDownGui;
import catto.uwu.ui.clickgui.myth.MythClickGui;
import catto.uwu.utils.client.ClientData;
import catto.uwu.utils.client.ClientDataFile;
import catto.uwu.processor.ProcessorManager;
import catto.uwu.viamcp.viamcp.ViaMCP;

public class Client {
    public static Client instance;

    //Info
    public static String clientName = "Catto", clientVersion = "Gato undead", build = "081322";
    public static int versionNumber = 0;
    public static boolean load = false;

    //Instance
    public ModuleManager moduleManager;
    public FileFactory fileFactory;
    public ConfigManager configManager;
    public CommandManager commandManager;
    public MythClickGui mythClickGui;
    public DropDownGui dropDownGui;

    //Data
    public static ClientData clientData;
    public ProcessorManager processorManager;
    public boolean confMkdir = false;

    public void startClient(){
        if (instance!=null) return;

        instance = this;

        initViaMCP();

        moduleManager = new ModuleManager();
        fileFactory = new FileFactory();
        commandManager = new CommandManager();
        configManager = new ConfigManager();
        processorManager = new ProcessorManager();

        moduleManager.registerNormal();
        fileFactory.setupRoot(clientName);
        fileFactory.add(
                new ModulesFile(),
                new ClientDataFile()
        );
        this.fileFactory.load();

        EventManager.register(this);

        mythClickGui = new MythClickGui();
        dropDownGui = new DropDownGui();

        load = true;
    }

    public void bootClient() {

    }

    public void shutdownClient() {
        if (instance==null) return;

        try {
            EventManager.unregister(instance);
            instance.fileFactory.save();
        } catch (Exception e) {
            clientData.logError("Error while saving files", e);
        }
    }

    private void initViaMCP(){
        try {
            ViaMCP.create();

            // In case you want a version slider like in the Minecraft options, you can use this code here, please choose one of those:

            ViaMCP.INSTANCE.initAsyncSlider(); // For top left aligned slider
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventTarget
    public void onKeyUpdate(EventKey event){
        ModuleManager.getModules().stream().filter(module -> module.getKeyCode() == event.getKey()).forEach(Module::toggle);
    }
}
