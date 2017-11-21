package generics;

import lombok.Getter;

import javax.ws.rs.core.Cookie;
import java.net.*;

public class CookieGenerator {

    private static InetAddress inetAddress;
    private static NetworkInterface networkInterface;

    private byte[] IPv4Address;
    private byte[] MACAddress;

    @Getter
    private Cookie cookie;

    public CookieGenerator() {
        super();
        try {
            try {
                inetAddress = Inet4Address.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            networkInterface = NetworkInterface.getByInetAddress(inetAddress);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CookieGenerator cookieGenerator = new CookieGenerator();
        cookieGenerator.fetchIPv4Address();
        cookieGenerator.fetchMACAddress();
    }

    private CookieGenerator fetchIPv4Address() {
        IPv4Address = inetAddress.getAddress();
        System.out.println(IPv4Address[0] + "." + IPv4Address[1] + "." + IPv4Address[2] + "." + IPv4Address[3]);
        return this;
    }

    private CookieGenerator fetchMACAddress() {
        try {
            MACAddress = networkInterface.getHardwareAddress();
            System.out.println(MACAddress[0]);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CookieGenerator generateStandardCookie() {

        this.fetchIPv4Address().fetchMACAddress();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(IPv4Address.toString());
        stringBuilder.append(MACAddress.toString());
        /*System.out.println(stringBuilder);*/

        cookie = new Cookie("cashless-pay", new String(stringBuilder));
        return this;
    }
}