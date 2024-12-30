package kseoni.ch.pkmn.core.dao;

import kseoni.ch.pkmn.core.CoreConfiguration;
import kseoni.ch.pkmn.shared.dto.StudentDto;
import kseoni.ch.pkmn.shared.entities.CardEntity;
import kseoni.ch.pkmn.core.repositories.CardEntityRepository;
import kseoni.ch.pkmn.shared.dto.CardEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CardDao {

    private CardEntityRepository cardEntityRepository;

    public CardDao(@Autowired CardEntityRepository cardEntityRepository){
        this.cardEntityRepository = cardEntityRepository;
    }

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
