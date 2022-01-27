package ru.job4j.dream.utils;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ReadPropertiesTest {

    @Test
    public void getPathWithCorrect() {
        String path = "src/test/resources/app.properties";
        ReadProperties prop = new ReadProperties(path);
        prop.load();
        assertThat(prop.getPath("default.image"), is("C:\\images\\0.jpg"));
        assertThat(prop.getPath("default.dir"), is("C:\\images"));
    }

    @Test
    public void getPathWhenNotFoundKey() {
        String path = "src/test/resources/app.properties";
        ReadProperties prop = new ReadProperties(path);
        prop.load();
        assertNull(prop.getPath("defaultimage"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getPathWhenExceptionPathIsDir() {
        String path = "src/test/resources/";
        ReadProperties prop = new ReadProperties(path);
    }

    @Test (expected = IllegalArgumentException.class)
    public void getPathWhenExceptionPathNotExist() {
        String path = "src/test/resources/p.properties";
        ReadProperties prop = new ReadProperties(path);
    }

    @Test
    public void getPathWhenReadFileWithComment() {
        String path = "src/test/resources/app_with_comment.properties";
        ReadProperties prop = new ReadProperties(path);
        prop.load();
        assertThat(prop.getPath("default.image"), is("C:\\images\\0.jpg"));
        assertThat(prop.getPath("default.dir"), is("C:\\images"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getPathWhenReadFileWithoutValue() {
        String path = "src/test/resources/app_without_value.properties";
        ReadProperties prop = new ReadProperties(path);
        prop.load();
    }

    @Test (expected = IllegalArgumentException.class)
    public void getPathWhenReadFileWithoutKey() {
        String path = "src/test/resources/app_without_key.properties";
        ReadProperties prop = new ReadProperties(path);
        prop.load();
    }

    @Test (expected = IllegalArgumentException.class)
    public void getPathWhenReadFileWithoutEqually() {
        String path = "src/test/resources/app_without_equally.properties";
        ReadProperties prop = new ReadProperties(path);
        prop.load();
    }
}