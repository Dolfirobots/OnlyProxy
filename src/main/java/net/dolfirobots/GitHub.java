package net.dolfirobots;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class GitHub {
    // Not the best version checker, but it works :)
    
    public static String getLastedVersion(String user, String repo) {
        try {
            URL url = new URL("https://api.github.com/repos/" + user + "/" + repo + "/releases/latest");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
            if (conn.getResponseCode() == 200) {
                try (InputStream is = conn.getInputStream();
                     Scanner s = new Scanner(is).useDelimiter("\\A")) {
                    String response = s.hasNext() ? s.next() : "";
                    int tagIndex = response.indexOf("\"tag_name\":\"");
                    if (tagIndex != -1) {
                        int start = tagIndex + 12;
                        int end = response.indexOf("\"", start);
                        return response.substring(start, end);
                    }
                }
            }
            return "unknown";
        } catch (Exception e) {
            Main.sendMessage("§cError by fetching GitHub version: " + e.getMessage());
            return "unknown";
        }
    }
    public static ArrayList<String> getAllVersions(String user, String repo) {
        ArrayList<String> versions = new ArrayList<>();
        try {
            URL url = new URL("https://api.github.com/repos/" + user + "/" + repo + "/releases");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
            if (conn.getResponseCode() == 200) {
                try (InputStream is = conn.getInputStream();
                     Scanner s = new Scanner(is).useDelimiter("\\A")) {
                    String response = s.hasNext() ? s.next() : "";
                    int index = 0;
                    while ((index = response.indexOf("\"tag_name\":\"", index)) != -1) {
                        int start = index + 12;
                        int end = response.indexOf("\"", start);
                        if (end != -1) {
                            versions.add(response.substring(start, end));
                            index = end;
                        } else {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Main.sendMessage("§cError by fetching GitHub versions: " + e.getMessage());
        }
        return versions;
    }
}
