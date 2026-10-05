package com.siem.SecurityEvent;

import com.maxmind.db.Reader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.InetAddress;
import java.util.Map;

@Service
public class GeoLocationData {

    private final Reader ipInfoDatabaseReader;

    public GeoLocationData() throws Exception {
        InputStream ipInfoDatabase = getClass().getResourceAsStream("/ipinfo_lite.mmdb");
        ipInfoDatabaseReader = new Reader(ipInfoDatabase);
    }

    public String findCountry(String ipAddress) throws Exception {
        Map<String, Object> location = ipInfoDatabaseReader.get(InetAddress.getByName(ipAddress), Map.class);
        if (location == null) {
            return "Unknown";
        }
        return (String) location.get("country");
    }
}