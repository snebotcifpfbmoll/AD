package com.snebot.ad.workspace.helper;

import com.snebot.ad.workspace.data.ConfigValues;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileUtils {
    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static String getUserPath(String name) {
        String fileSeparator = System.getProperty("file.separator");
        String fileName = StringUtils.substringAfterLast(name, fileSeparator);
        if (fileName.equals("")) fileName = name;
        return String.format("%s%s%s", System.getProperty("user.home"), fileSeparator, fileName);
    }

    public static File createFile(String path, String content) {
        File file = null;
        FileWriter fileWriter = null;

        try {
            file = new File(path);
            if (!file.getParentFile().mkdirs()) {
                String dirs = StringUtils.substringBeforeLast(path, System.getProperty("file.separator"));
                log.error(String.format("failed to create parent directories: %s", dirs));
            }

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
        } catch (Exception e) {
            log.error("failed to create file ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (Exception e) {
                    log.error("failed to close file writer ", e);
                }
            }
        }

        return file;
    }

    public static File createUserFile(String name, String content) {
        return createFile(getUserPath(name), content);
    }

    public static List<String> readFileLines(String path) {
        ArrayList<String> ret = new ArrayList<>();
        BufferedReader buffer = null;
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            buffer = new BufferedReader(fileReader);
            String line = null;
            while ((line = buffer.readLine()) != null) {
                ret.add(line);
            }
        } catch (Exception e) {
            log.error("Failed to read file ", e);
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (Exception e) {
                    log.error("Failed to close buffer ", e);
                }
            }
        }

        return ret;
    }

    public ConfigValues loadProperties(String path) {
        try {
            ConfigValues configValues = new ConfigValues();
            Properties properties = new Properties();
            properties.load(new FileInputStream(path));
            configValues.setUser(properties.getProperty(ConfigurationProperties.user.toString()));
            configValues.setPassword(properties.getProperty(ConfigurationProperties.password.toString()));
            configValues.setServer(properties.getProperty(ConfigurationProperties.server.toString()));
            configValues.setPort(properties.getProperty(ConfigurationProperties.port.toString()));
            return configValues;
        } catch (Exception e) {
            log.error("failed to load properties ", e);
            return null;
        }
    }

    public void saveProperties(ConfigValues data, String path) {
        try {
            Properties properties = new Properties();
            properties.setProperty(ConfigurationProperties.user.toString(), data.getUser());
            properties.setProperty(ConfigurationProperties.password.toString(), data.getPassword());
            properties.setProperty(ConfigurationProperties.server.toString(), data.getServer());
            properties.setProperty(ConfigurationProperties.port.toString(), data.getPort());
            properties.store(new FileOutputStream(path), "Comentario");
        } catch (Exception e) {
            log.error("failed to save properties ", e);
        }
    }
}
