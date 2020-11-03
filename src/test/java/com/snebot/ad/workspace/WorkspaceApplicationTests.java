package com.snebot.ad.workspace;

import com.snebot.ad.workspace.data.ConfigValues;
import com.snebot.ad.workspace.data.Student;
import com.snebot.ad.workspace.helper.DummyUtils;
import com.snebot.ad.workspace.helper.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;

@SpringBootTest
class WorkspaceApplicationTests {

    @Test
    void trySaveFile() {
        String filePath = String.format("%s/test/file.txt", System.getProperty("user.home"));
        File file = FileUtils.createFile(filePath, "test content");
        Assert.notNull(file, "failed to create file");
    }

    @Test
    void trySaveUserFile() {
        String filePath = "userfile.txt";
        File file = FileUtils.createUserFile(filePath, "test user file content");
        Assert.notNull(file, "failed to create user file");
    }

    @Test
    void tryCreateAndReadFile() {
        String filePath = String.format("%s/file.txt", System.getProperty("user.home"));
        File userFile = FileUtils.createFile(filePath, "first line\nsecond line\nthird line");
        Assert.notNull(userFile, String.format("Failed to create user file: %s", filePath));

        List<String> fileContent = FileUtils.readFileLines(filePath);
        Assert.notEmpty(fileContent, "File is empty");
    }

    @Test
    void validateProperties() {
        String filePath = String.format("%s/config.properties", System.getProperty("user.home"));

        ConfigValues configValues = new ConfigValues();
        configValues.setUser("snebot");
        configValues.setPassword("password");
        configValues.setServer("localhost");
        configValues.setPort("3306");

        FileUtils fileUtils  = new FileUtils();
        fileUtils.saveProperties(configValues, filePath);

        ConfigValues configValues2 = fileUtils.loadProperties(filePath);
        Assert.isTrue(configValues.equals(configValues2), "Config values are not equal");
    }

    @Test
    void tryDummyUtils() {
        DummyUtils dummyUtils = new DummyUtils();
        List<Student> students = dummyUtils.generateObjects(Student.class, 50);
        Assert.notNull(students, "failed to create student list");
    }
}