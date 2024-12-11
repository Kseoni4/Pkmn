package kseoni.ch.pkmn.apiclient;

import com.fasterxml.jackson.databind.JsonNode;
import kseoni.ch.pkmn.models.Card;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Component
public class PokemonApiClient {

    @Value("${pokemontcg.url}")
    private String API_URL;

    @SneakyThrows
    public String getImageFromApi(Card card){

        String query = PokemonTcgQuery.builder()
                        .setName(card.getName())
                        .setNumber(card.getCardNumber())
                .build();

         RestClient request = RestClient.builder()
                .baseUrl(API_URL)
                .build();

         JsonNode json = request.get()
                 .uri(query)
                 .retrieve()
                 .body(JsonNode.class);

        if(Objects.isNull(json)){
            return "None";
        }

        String imageUrl = json.findParent("images")
                .findValue("large")
                .asText();

        log.info("Image URL: {}", imageUrl);

        return imageUrl;
    }
}
