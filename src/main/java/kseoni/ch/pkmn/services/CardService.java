package kseoni.ch.pkmn.services;

import kseoni.ch.pkmn.dto.StudentDto;
import kseoni.ch.pkmn.dto.CardEntityDto;
import kseoni.ch.pkmn.models.Card;

import java.util.List;

public interface CardService {

    CardEntityDto getCardInfo(String name);

    List<CardEntityDto> getCardsInfo();

    CardEntityDto createCard(Card card);

    CardEntityDto getCardByOwner(StudentDto owner);
}
