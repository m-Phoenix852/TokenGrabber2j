package me.Phoenix852.TokenGrabber2j.Utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class User {

    public String getUsername() {
        return System.getProperty("user.name");
    }

    public String getIPAddress() throws UnknownHostException {
        String IP = InetAddress.getLocalHost().getHostAddress();
        return IP;
    }

    public String getOS() {
        String os = System.getProperty("os.name");
        return os;
    }

}
