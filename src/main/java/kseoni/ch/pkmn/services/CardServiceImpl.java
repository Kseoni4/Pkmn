package kseoni.ch.pkmn.services;

import kseoni.ch.pkmn.apiclient.PokemonApiClient;
import kseoni.ch.pkmn.dao.CardDao;
import kseoni.ch.pkmn.dto.StudentDto;
import kseoni.ch.pkmn.dto.CardEntityDto;
import kseoni.ch.pkmn.entities.CardEntity;
import kseoni.ch.pkmn.models.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {
    private final CardDao cardDao;

    private final PokemonApiClient pokemonApiClient;

    public String getCardImage(Card card){
        return pokemonApiClient.getImageFromApi(card);
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
