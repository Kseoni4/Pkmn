package kseoni.ch.pkmn.core.services;

import kseoni.ch.pkmn.apiclient.PokemonApiClient;
import kseoni.ch.pkmn.core.dao.CardDao;
import kseoni.ch.pkmn.core.dao.ImageDao;
import kseoni.ch.pkmn.shared.dto.StudentDto;
import kseoni.ch.pkmn.shared.dto.CardEntityDto;
import kseoni.ch.pkmn.shared.entities.CardEntity;
import kseoni.ch.pkmn.shared.models.Card;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final ImageDao imageDao;

    private final CardDao cardDao;

    private final PokemonApiClient pokemonApiClient;

    public String getCardImage(Card card){
        return getCardImage(card.getName(), card.getCardNumber());
    }

    public String getCardImage(String cardName, String cardNumber){
        String url = imageDao.loadImgUrl(cardName);

        if(url == null || url.isEmpty()){
            log.info("Image not found in cache, get from api");
            url = pokemonApiClient.getImageFromApi(cardName, cardNumber);
            imageDao.writeImgUrlToCache(cardName, url);
        }

        return url;

    }

    @Override
    public CardEntityDto getCardInfo(String name) {
        return cardDao.getCardsByName(name).get(0);
    }

    @Override
    public List<CardEntityDto> getCardsInfo() {
        return cardDao.getCards().stream().map(CardEntityDto::fromEntity).toList();
    }

    @Override
    public CardEntityDto createCard(Card card) {
        CardEntity entity = cardDao.saveCard(CardEntity.fromModel(card));
        return CardEntityDto.fromEntity(entity);
    }

    @Override
    public CardEntityDto getCardByOwner(StudentDto owner) {
        CardEntity entity = cardDao.getCardByOwner(owner);

        if(Objects.isNull(entity)){
            return null;
        }

        return CardEntityDto.fromEntity(entity);
    }
}
