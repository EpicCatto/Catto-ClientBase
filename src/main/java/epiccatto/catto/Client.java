package epiccatto.catto;

import epiccatto.catto.command.CommandManager;
import epiccatto.catto.event.EventManager;
import epiccatto.catto.event.EventTarget;
import epiccatto.catto.event.impl.EventKey;
import epiccatto.catto.module.api.Module;
import epiccatto.catto.module.api.ModuleManager;
import epiccatto.catto.module.file.FileFactory;
import epiccatto.catto.module.file.config.ConfigManager;
import epiccatto.catto.processor.ProcessorManager;
import epiccatto.catto.ui.clickgui.myth.MythClickGui;
import epiccatto.catto.utils.client.ClientDataFile;
import epiccatto.catto.module.file.impl.ModulesFile;
import epiccatto.catto.utils.client.ClientData;

import java.util.Date;

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
    //Data
    public static ClientData clientData;
    public ProcessorManager processorManager;
    public boolean confMkdir = false;

    public void startClient(){
        if (instance!=null) return;
        System.out.println(new Date().getTime());
        instance = this;

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

    @EventTarget
    public void onKeyUpdate(EventKey event){
        ModuleManager.getModules().stream().filter(module -> module.getKeyCode() == event.getKey()).forEach(Module::toggle);
    }
}
