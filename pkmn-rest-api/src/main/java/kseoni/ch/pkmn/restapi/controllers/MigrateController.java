package kseoni.ch.pkmn.restapi.controllers;

import kseoni.ch.pkmn.core.services.MigrateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/migrate")
@RequiredArgsConstructor
public class MigrateController {
    private final MigrateService migrateService;

    @GetMapping
    public String migrateDatabase(){
        return migrateService.migrateDatabase() ? "OK" : "Error while migrating database";
    }
}
