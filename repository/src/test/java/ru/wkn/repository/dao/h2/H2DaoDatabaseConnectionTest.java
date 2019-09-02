package ru.wkn.repository.dao.h2;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.wkn.entries.server.plaintext.ProtocolType;
import ru.wkn.entries.server.plaintext.ServerEntry;
import ru.wkn.repository.dao.EntityInstance;
import ru.wkn.repository.exceptions.PersistenceException;

import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

class H2DaoDatabaseConnectionTest {

    private static H2Dao<ServerEntry, Long> serverEntryLongH2Dao;
    private static ServerEntry serverEntry;

    @BeforeAll
    static void initDao() {
        serverEntryLongH2Dao = new H2Dao<>(ServerEntry.class,
                (Session) Persistence.createEntityManagerFactory("h2_entity_manager")
                        .createEntityManager().getDelegate(),
                EntityInstance.NETWORK_ACCESS);
        serverEntry = new ServerEntry("localhost", 8080, ProtocolType.TCP);
    }

    @Test
    void testCreating() throws PersistenceException {
        serverEntryLongH2Dao.create(serverEntry);
        assertEquals(serverEntry, serverEntryLongH2Dao.read((long) 1));
    }

    @AfterAll
    static void clearDatabase() throws PersistenceException {
        serverEntryLongH2Dao.delete(serverEntry);
    }
}
