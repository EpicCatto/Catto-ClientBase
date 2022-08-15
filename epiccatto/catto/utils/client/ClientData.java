package epiccatto.catto.utils.client;

public class ClientData {
    //Encryption
    private String secret;
    private String salt;
    //Data
    private String authToken;
    private String hwid;

    public ClientData(String secret, String salt, String authToken, String hwid) {
        this.secret = secret;
        this.salt = salt;
        this.authToken = authToken;
        this.hwid = hwid;
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
}
