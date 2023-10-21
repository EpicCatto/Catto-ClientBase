package catto.uwu.utils.client;

import java.util.Date;
import java.util.HashMap;

public class ClientData {
    //Encryption
    private String secret;
    private String salt;
    //Data
    private String authToken;
    private String hwid;
    private final HashMap<String, Throwable> stackErrorLogs;

    public ClientData(String secret, String salt, String authToken, String hwid) {
        this.secret = secret;
        this.salt = salt;
        this.authToken = authToken;
        this.hwid = hwid;
        this.stackErrorLogs = new HashMap<>();
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getHwid() {
        return hwid;
    }

    public void setHwid(String hwid) {
        this.hwid = hwid;
    }

    public void logError(String errorTitle, Throwable info) {
        System.err.println("[ERROR] " + errorTitle + ": " + info.getMessage());
        info.printStackTrace();
        stackErrorLogs.put("[" + new Date() + "] " + errorTitle, info);
    }


    public HashMap<String, Throwable> getStackErrorLogs() {
        return stackErrorLogs;
    }
}
