package ru.job4j.dream.utils;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ReadPropertiesTest {

    @Test
    public void getPathWhenReadFileWithComment() {
        ReadProperties prop = new ReadProperties();
        assertThat(prop.getPath("default.image"), is("C:\\images\\0.jpg"));
        assertThat(prop.getPath("default.dir"), is("C:\\images"));
    }

    @Test
    public void getPathWhenNotFoundKey() {
        ReadProperties prop = new ReadProperties();
        assertNull(prop.getPath("defaultimage"));
    }
}