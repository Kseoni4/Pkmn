package kseoni.ch.pkmn.core.services;

import kseoni.ch.pkmn.shared.dto.StudentDto;
import kseoni.ch.pkmn.shared.dto.CardEntityDto;
import kseoni.ch.pkmn.shared.models.Card;

import java.util.List;

public interface CardService {

    String getCardImage(Card card);

    String getCardImage(String cardName, String cardNumber);

    CardEntityDto getCardInfo(String name);

    List<CardEntityDto> getCardsInfo();

    CardEntityDto createCard(Card card);

    CardEntityDto getCardByOwner(StudentDto owner);
}
