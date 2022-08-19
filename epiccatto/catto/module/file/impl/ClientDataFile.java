package epiccatto.catto.module.file.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import epiccatto.catto.Client;
import epiccatto.catto.module.file.IFile;
import epiccatto.catto.utils.authentication.Encryption;
import epiccatto.catto.utils.authentication.HWID;
import epiccatto.catto.utils.client.ClientData;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class ClientDataFile implements IFile {

    private File file;

    //Encryption
    private String secret;
    private String salt;

    //Data
    private String authToken;
    private String hwid;

    @Override
    public void save(Gson gson) {

        if (secret == null || salt == null || authToken == null) {
            secret = Encryption.generateSecret();
            salt = Encryption.generateSalt();
            authToken = "waiting";
            hwid = HWID.getComputerHWID();
            if (Client.clientData == null)
                Client.clientData = new ClientData(secret, salt, authToken, hwid);
        }

        JsonObject object = new JsonObject();

        //Client Data keys
        JsonObject dataKeyObject = new JsonObject();
        dataKeyObject.addProperty("Secret", Client.clientData.getSecret());
        dataKeyObject.addProperty("Salt", Client.clientData.getSalt());
        object.add("DO NOT TOUCH IF YOU DONT KNOW WHAT YOU ARE DOING", dataKeyObject);

        //Client info
        JsonObject dataObject = new JsonObject();
        dataObject.addProperty("version", Client.versionNumber);
        dataObject.addProperty("auth-token", Client.clientData.getAuthToken());
        dataObject.addProperty("hwid", Client.clientData.getHwid());
        JsonObject errorsLogs = new JsonObject();

        for (String key : Client.clientData.getErrorLogs().keySet()) {
            JsonObject error = new JsonObject();
            error.addProperty("title", key);
            error.addProperty("message", Client.clientData.getErrorLogs().get(key).getMessage());
            error.addProperty("stacktrace", Arrays.toString(Client.clientData.getErrorLogs().get(key).getStackTrace()));
            errorsLogs.add(key, error);
        }

        dataObject.add("error-logs", errorsLogs);

        JsonObject dataObjectOut = new JsonObject();
        try {
            dataObjectOut.addProperty("Data", Encryption.encrypt(gson.toJson(dataObject), secret, salt));
        }catch (Exception e){
            Client.clientData.logError("Error while saving client data Json", e);
        }
        object.add("Data (DO NOT TOUCH)", dataObjectOut);

        writeFile(gson.toJson(object), file);

        load(gson);
    }

    @Override
    public void load(Gson gson) {
        if (!file.exists()) {
            save(gson);
        }
        try {
            //Load client data
            //Load client data keys
            JsonObject object = gson.fromJson(readFile(file), JsonObject.class);
            JsonObject dataKeyObject = object.getAsJsonObject("DO NOT TOUCH IF YOU DONT KNOW WHAT YOU ARE DOING");
            secret = dataKeyObject.get("Secret").getAsString();
            salt = dataKeyObject.get("Salt").getAsString();
            //Load client info
            JsonObject dataObject = gson.fromJson(Encryption.decrypt(object.get("Data (DO NOT TOUCH)").getAsJsonObject().get("Data").getAsString(), secret, salt), JsonObject.class);
            authToken = dataObject.get("auth-token").getAsString();
            hwid = dataObject.get("hwid").getAsString();

//            System.out.println(dataObject);
        } catch (Exception e) {
            System.err.println("Error while loading client data resetting...");
//            Client.clientData.logError("Error while loading client data Json", e);
            save(gson);
            return;
        }
        if (Client.clientData == null)
            Client.clientData = new ClientData(secret, salt, authToken, hwid);

    }

    @Override
    public void setFile(File root) {
        file = new File(root, "/data.catto");
    }
}
