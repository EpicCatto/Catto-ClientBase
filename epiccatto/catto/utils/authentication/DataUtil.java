package epiccatto.catto.utils.authentication;

import com.google.gson.JsonObject;
import epiccatto.catto.Client;
import epiccatto.catto.event.Data;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class DataUtil {

    private static final String SERVER = "https://notthatuwu-bot-backup.herokuapp.com";
    private static final String USER_AGENT = "GATO-API/";

    /*
     * hi i use intent api dont mind me :troll:
     */
    public static void main(String[] args) {
        try {
            System.out.println(getCurSha256());
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("uid", "1");
            jsonObject.addProperty("hwid", HWID.getComputerHWID());

            //jsonObject.add("data", data);

            System.out.println(jsonObject.toString());

            System.out.println("Respond: " + DataUtil.postResponse("/auth", jsonObject.toString()));
        } catch (IOException | URISyntaxException e) {
            Client.clientData.logError("Error while connecting to the auth server", e);
        }
    }

    public static String getResponse(final String getParameters) throws IOException {
        final HttpsURLConnection connection = (HttpsURLConnection) new URL(SERVER + getParameters).openConnection();
        connection.addRequestProperty("User-Agent", USER_AGENT);

        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String lineBuffer;
        final StringBuilder response = new StringBuilder();
        while ((lineBuffer = reader.readLine()) != null)
            response.append(lineBuffer);

        return response.toString();
    }

    public static String postResponse(final String getParameters, String data) throws IOException {
        final HttpsURLConnection connection = (HttpsURLConnection) new URL(SERVER + getParameters).openConnection();
        connection.addRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        final byte[] out = data.getBytes(StandardCharsets.UTF_8);
        final int length = out.length;
        connection.setFixedLengthStreamingMode(length);
        connection.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.addRequestProperty("Accept", "application/json");
        connection.connect();
        try (OutputStream os = connection.getOutputStream()) {
            os.write(out);
        }

        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String lineBuffer;
        StringBuilder response = new StringBuilder();
        while ((lineBuffer = reader.readLine()) != null)
            response.append(lineBuffer);

        return response.toString();
    }

    public static String dev(final String getParameters, String data) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:5000" + getParameters).openConnection();
        connection.addRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        final byte[] out = data.getBytes(StandardCharsets.UTF_8);
        final int length = out.length;
        connection.setFixedLengthStreamingMode(length);
        connection.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.addRequestProperty("Accept", "application/json");
        connection.connect();
        try (OutputStream os = connection.getOutputStream()) {
            os.write(out);
        }

        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String lineBuffer;
        StringBuilder response = new StringBuilder();
        while ((lineBuffer = reader.readLine()) != null)
            response.append(lineBuffer);

        return response.toString();
    }

    public static String getCurSha256() throws URISyntaxException {
        File currentJavaJarFile = new File(getJarPath());
        String filepath = currentJavaJarFile.getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");// MD5
            FileInputStream fis = new FileInputStream(filepath);
            byte[] dataBytes = new byte[1024];
            int nread = 0;

            while((nread = fis.read(dataBytes)) != -1)
                md.update(dataBytes, 0, nread);

            byte[] mdbytes = md.digest();

            for(int i=0; i<mdbytes.length; i++)
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100 , 16).substring(1));
        }
        catch(NoSuchAlgorithmException | IOException e)
        {
            Client.clientData.logError("Error while getting Current Sha 256", e);
        }

        return sb.toString();
    }

    public static String getJarPath() throws URISyntaxException {
        CodeSource codeSource = Data.class.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        return jarFile.getParentFile().getPath();
    }

}
