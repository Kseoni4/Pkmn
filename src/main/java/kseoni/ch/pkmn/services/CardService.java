package kseoni.ch.pkmn.services;

import kseoni.ch.pkmn.dto.StudentDto;
import kseoni.ch.pkmn.dto.CardEntityDto;
import kseoni.ch.pkmn.models.Card;

import java.util.List;

public interface CardService {

    String getCardImage(Card card);

    String getCardImage(String cardName, String cardNumber);

    CardEntityDto getCardInfo(String name);

    List<CardEntityDto> getCardsInfo();

    CardEntityDto createCard(Card card);

    CardEntityDto getCardByOwner(StudentDto owner);
}
