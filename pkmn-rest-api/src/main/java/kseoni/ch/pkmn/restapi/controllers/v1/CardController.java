package kseoni.ch.pkmn.restapi.controllers.v1;

import kseoni.ch.pkmn.core.dao.CardDao;
import kseoni.ch.pkmn.shared.dto.StudentDto;
import kseoni.ch.pkmn.shared.entities.CardEntity;
import kseoni.ch.pkmn.shared.dto.CardEntityDto;
import kseoni.ch.pkmn.shared.models.Card;
import kseoni.ch.pkmn.core.repositories.CardEntityRepository;
import kseoni.ch.pkmn.core.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardDao cardDao;

    private final CardEntityRepository cardEntityRepository;

    private final CardService cardService;

    @GetMapping("/img")
    public String getCardImage(@RequestBody Card card){
        return cardService.getCardImage(card);
    }

    @GetMapping
    public PagedModel<CardEntityDto> getCardList(Pageable pageable) {
        Page<CardEntityDto> cardDtos = cardEntityRepository.findAll(pageable).map(CardEntityDto::fromEntity);
        return new PagedModel<>(cardDtos);
    }

    @GetMapping("/{name}")
    public List<CardEntityDto> getInfo(@PathVariable String name) {
        System.out.println("Getting info for " + name);
        List<CardEntityDto> infos = cardDao.getCardsByName(name);
        System.out.println(infos);
        return infos;
    }

    @GetMapping("/id/{id}")
    public CardEntity getOne(@PathVariable UUID id) {
        return cardDao.getCardById(id);
    }

    @GetMapping("/by-owner")
    public CardEntityDto getCardByOwner(@RequestBody StudentDto studentDto) {
        CardEntity cardEntity = cardDao.getCardByOwner(studentDto);
        return CardEntityDto.fromEntity(cardEntity);
    }

    @GetMapping("/by-ids")
    public List<CardEntity> getMany(@RequestParam List<UUID> ids) {
        return null;//cardEntityRepository.findAllById(ids);
    }

    @PostMapping
    public CardEntity create(@RequestBody CardEntity cardEntity) {
        System.out.println(cardEntity.getName());
        return cardDao.saveCard(cardEntity);
    }
}
