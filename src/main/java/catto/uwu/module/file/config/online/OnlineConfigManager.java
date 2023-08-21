package catto.uwu.module.file.config.online;

import catto.uwu.Client;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class OnlineConfigManager {

    public static ArrayList<OnlineConfig> loadConfigs() {
        final ArrayList<OnlineConfig> loadedConfigs = new ArrayList<>();
        new Thread(() -> {
            try {
                final URL url = new URL("https://raw.githubusercontent.com/EpicCatto/Myth-Public/main/Online%20Configs");
                final Scanner s = new Scanner(url.openStream());

                while (s.hasNext()) {
                    final String[] s2 = s.nextLine().split(":");

                    LocalDateTime now = LocalDateTime.now();

                    Period p = Period.between(new Date(Long.parseLong(s2[2])).toInstant().atZone(ZoneId.of("GMT")).toLocalDate(), now.toLocalDate());

                    loadedConfigs.add(new OnlineConfig(s2[0], s2[1], s2[3], "https://raw.githubusercontent.com/EpicCatto/Myth-Public/main/Configs/"+ s2[0] + ".json", ""+ p.getDays()));
                }

            } catch (Exception e) {
                Client.clientData.logError("Error while loading online config", e);
                loadedConfigs.add(new OnlineConfig(EnumChatFormatting.RED + "Online Config Error", EnumChatFormatting.RED + "NO DATA", EnumChatFormatting.RED + "Error: " + e.getMessage(), EnumChatFormatting.RED + "ERROR", new Date().toGMTString()));
            }
        }).run();
        return loadedConfigs;
    }

    public static String jsonGetRequest(String urlQueryString) {
        String json = null;
        try {
            URL url = new URL(urlQueryString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inStream = connection.getInputStream();
            json = streamToString(inStream); // input stream to string
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }

}
