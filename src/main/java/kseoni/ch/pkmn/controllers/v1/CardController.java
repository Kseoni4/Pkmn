package kseoni.ch.pkmn.controllers.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kseoni.ch.pkmn.dao.CardDao;
import kseoni.ch.pkmn.dto.StudentDto;
import kseoni.ch.pkmn.entities.CardEntity;
import kseoni.ch.pkmn.dto.CardEntityDto;
import kseoni.ch.pkmn.models.Card;
import kseoni.ch.pkmn.repositories.CardEntityRepository;
import kseoni.ch.pkmn.services.CardService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
