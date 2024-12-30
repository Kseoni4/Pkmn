package kseoni.ch.pkmn.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MigrateService {

    private final Flyway flyway;

    public boolean migrateDatabase(){
        MigrateResult result = flyway.migrate();
        log.info("Migrated database {}, migrations: {}", result.database, result.migrations);
        return result.success;
    }
}
