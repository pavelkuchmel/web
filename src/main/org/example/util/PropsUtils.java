package main.org.example.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class PropsUtils {

    private Properties props;

    public PropsUtils(String path) throws IOException {
        props = new Properties();
        props.load(new FileInputStream(path));
    }

    public List<String> getUrlPatterns(String roleName) {
        Object val = props.get(roleName.toUpperCase(Locale.ROOT).replace(' ','_')); // Manager -> MANAGER
        List<String> urls = Arrays.asList(((String) val).split(", "));
        return urls;
    }


    public static void main(String[] args) throws IOException {
        PropsUtils propsUtils = new PropsUtils("src/main/resources/ROLES_URL_PATTERNS_MAPPING.properties");
        List<String> urls = propsUtils.getUrlPatterns("Manager");
        System.out.println(urls);
    }
}
