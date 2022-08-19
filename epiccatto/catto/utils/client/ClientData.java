package epiccatto.catto.utils.client;

import java.util.Date;
import java.util.HashMap;

public class ClientData {
    //Encryption
    private String secret;
    private String salt;
    //Data
    private String authToken;
    private String hwid;
    private final HashMap<String, Exception> errorLogs;

    public ClientData(String secret, String salt, String authToken, String hwid) {
        this.secret = secret;
        this.salt = salt;
        this.authToken = authToken;
        this.hwid = hwid;
        this.errorLogs = new HashMap<>();
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

    public void logError(String errorTitle, Exception info) {
        System.err.println("[ERROR] " + errorTitle + ": " + info.getMessage());
        info.printStackTrace();
        errorLogs.put("[" + new Date().toString() + "] " + errorTitle, info);
    }

    public HashMap<String, Exception> getErrorLogs() {
        return errorLogs;
    }
}
