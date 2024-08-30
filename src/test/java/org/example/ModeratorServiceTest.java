package org.example;

import org.example.models.Moderator;
import org.example.repository.impl.ModeratorRepositoryImpl;
import org.example.service.ModeratorService;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class ModeratorServiceTest {
    private ModeratorService service;

    @Before
    public void setup() {
        Properties props = new Properties();
        try (InputStream is = new FileInputStream("src/test/resources/application-test.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application-test.properties", e);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(props.getProperty("spring.datasource.url"));
        dataSource.setUser(props.getProperty("spring.datasource.username"));
        dataSource.setPassword(props.getProperty("spring.datasource.password"));

        ModeratorRepositoryImpl repository = new ModeratorRepositoryImpl(dataSource);
        service = new ModeratorService(repository);
    }

    @Test
    public void testCreateModerator() {
        Moderator moderator = new Moderator("Test moderator role");
        if (moderator.getModeratorRole() != null && !moderator.getModeratorRole().trim().isEmpty()) {
            Moderator savedModerator = service.saveModerator(moderator);
            assertNotNull(savedModerator);
            assertEquals("Test moderator role", savedModerator.getModeratorRole());
        } else {
            fail("Moderator is null or moderator role is null or empty");
        }
    }

    @Test
    public void testUpdateModerator() {
        Moderator moderator = service.getModeratorById(4);
        assertNotNull(moderator);
        moderator.setModeratorRole("Updated Test moderator role");
        service.updateModerator(moderator);
        Moderator updatedModerator = service.getModeratorById(4);
        assertNotNull(updatedModerator);
        assertEquals("Updated Test moderator role", updatedModerator.getModeratorRole());
    }

    @Test
    public void testReadModerator() {
        Moderator moderator = service.getModeratorById(4);
        assertNotNull(moderator);
        assertEquals("Updated Test moderator role", moderator.getModeratorRole());
    }

    @Test
    public void testDeleteModerator() {
        Moderator moderatorBeforeDeletion = service.getModeratorById(4);
        assertNotNull(moderatorBeforeDeletion);
        service.deleteModerator(4);
    }

    @Test
    public void testReadAllModerators() {
        List<Moderator> moderators = service.getAllModerators();
        assertNotNull(moderators);
        assertTrue(!moderators.isEmpty());
    }
}