package org.geekxiong.sws.tools;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {

    public static String getProperty(String key) throws Exception{
    	Properties prop = new Properties();
    	InputStream in = new FileInputStream(System.getProperty("user.dir")+"\\src\\contenttype.properties");
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        prop.load(bf);
        return prop.getProperty(key);
    }
}