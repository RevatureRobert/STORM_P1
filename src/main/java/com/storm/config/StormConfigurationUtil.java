package com.storm.config;

import com.storm.util.StormConfig;
import org.hibernate.cfg.AvailableSettings;

import java.util.Map;
import java.util.Properties;

public class StormConfigurationUtil {

    public static final String CONFIG_PREFIX = "storm";
    public static final String CONFIG_PREFIX_DATASOURCE = "storm.datasource";


    @SuppressWarnings("rawtypes")
    public static StormConfig loadConfiguration(Map props){
        Properties stormProps = new Properties();
        copyProperty(AvailableSettings.ISOLATION, props, "transactionIsolation", stormProps);
        copyProperty(AvailableSettings.AUTOCOMMIT, props, "autoCommit", stormProps);
        copyProperty(AvailableSettings.DRIVER, props, "driverClassName", stormProps);
        copyProperty(AvailableSettings.URL, props, "jdbcUrl", stormProps);
        copyProperty(AvailableSettings.USER, props, "username", stormProps);
        copyProperty(AvailableSettings.PASS, props, "password", stormProps);
        return new StormConfig(stormProps);
    }

    public static StormConfig loadConfiguration(Properties props){
        Properties stormProps = new Properties();
        copyProperty(AvailableSettings.DRIVER, props, "driverClassName", stormProps);
        copyProperty("jdbcUrl", props, "jdbcUrl", stormProps);
        copyProperty("username", props, "username", stormProps);
        copyProperty("password", props, "password", stormProps);

        return new StormConfig(stormProps);
    }

    @SuppressWarnings("rawtypes")
    private static void copyProperty(String srcKey, Map src, String dstKey, Properties dst)
    {
        if (src.containsKey(srcKey)) {
            dst.setProperty(dstKey, (String) src.get(srcKey));
        }
    }

    private static void copyProperty(String srcKey, Properties src, String dstKey, Properties dst)
    {
        if (src.containsKey(srcKey)) {
            dst.setProperty(dstKey, (String) src.get(srcKey));
        }
    }

}
