package epiccatto.catto;

import java.util.Date;

public class Client {
    public static Client instance;

    //Info
    public static String clientName = "Catto", clientVersion = "Gato undead", build = "081322";
    public static int versionNumber = 0;
    public static boolean load = false;

    public void startClient(){
        if (instance!=null) return;
        System.out.println(new Date().getTime());
        instance = this;

    }

    public static void preLoad() {

    }

    public static void shutdownClient() {

    }
}
