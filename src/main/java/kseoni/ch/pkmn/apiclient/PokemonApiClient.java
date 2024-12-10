package kseoni.ch.pkmn.apiclient;

import kseoni.ch.pkmn.models.Card;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;

//FeignClient(name = "pokemonclient", url = "${pokemontcg.url}")
public class PokemonApiClient {

    @Value("${pokemontcg.url}")
    private String API_URL;

    public void getImageFromApi(Card card){

    }
}
