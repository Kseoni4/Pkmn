package kseoni.ch.pkmn.dao;

import kseoni.ch.pkmn.apiclient.PokemonApiClient;
import kseoni.ch.pkmn.dto.StudentDto;
import kseoni.ch.pkmn.entities.CardEntity;
import kseoni.ch.pkmn.repositories.CardEntityRepository;
import kseoni.ch.pkmn.dto.CardEntityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CardDao {

    private final CardEntityRepository cardEntityRepository;

    public CardEntity saveCard(CardEntity cardEntity) {
        System.out.println(cardEntity);
        return cardEntityRepository.save(cardEntity);
    }

    public List<CardEntityDto> getCardsByName(String name) {
        return cardEntityRepository.findAllByName(name, CardEntityDto.class);
    }

    public CardEntity getCardById(UUID id) {
        return cardEntityRepository.findById(id).orElse(null);
    }

    public List<CardEntity> getCards(){
        return cardEntityRepository.findAll();
    }

    public CardEntity getCardByOwner(StudentDto owner){
        return cardEntityRepository.findByPokemonOwner(
                        owner.firstName(),
                        owner.surName(),
                        owner.familyName())
               .orElse(null);
    }
}
