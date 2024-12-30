package kseoni.ch.pkmn.mainapp.configurations;

import kseoni.ch.pkmn.core.CoreConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CoreConfiguration.class})
public class MainAppConfiguration {
}
