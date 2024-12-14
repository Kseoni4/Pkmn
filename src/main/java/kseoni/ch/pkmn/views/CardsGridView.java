package kseoni.ch.pkmn.views;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.PostConstruct;
import kseoni.ch.pkmn.apiclient.PokemonApiClient;
import kseoni.ch.pkmn.dto.CardEntityDto;
import kseoni.ch.pkmn.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Route("cards-grid")
@UIScope
@Service
@AnonymousAllowed
@RequiredArgsConstructor
public class CardsGridView extends VerticalLayout {

    private final CardService cardService;

    private final PokemonApiClient apiClient;

    @PostConstruct
    private void initUi(){
        Grid<CardEntityDto> grid = new Grid<>(CardEntityDto.class, false);
        grid.addColumn(new ComponentRenderer<>(
                card -> {
                    String url = cardService.getCardImage(card.name(), card.cardNumber());
                    Image image = new Image();
                    image.setSrc(url);
                    image.setHeight("200px");
                    image.setWidth("150px");
                    return image;
                }
        )).setHeader("Картинка").setWidth("150px");
        grid.addColumn(CardEntityDto::name).setHeader("Имя").setWidth("5rem");
        grid.addColumn(CardEntityDto::pokemonType).setHeader("Тип").setWidth("5rem");
        grid.addColumn(CardEntityDto::cardNumber).setHeader("Номер").setWidth("5rem");
        grid.addColumn(new ComponentRenderer<>(
                card -> {
                    Details ownerDetails = new Details();
                    ownerDetails.setOpened(true);
                    if(Objects.isNull(card.pokemonOwner())){
                        ownerDetails.add(new Span("Отсутствует"));
                        return ownerDetails;
                    }
                    VerticalLayout ownerLayout = new VerticalLayout(
                            new Span(card.pokemonOwner().getFamilyName()),
                            new Span(card.pokemonOwner().getFirstName()),
                            new Span(card.pokemonOwner().getGroup())
                    );

                    ownerDetails.add(ownerLayout);

                    return ownerDetails;
                }
        )).setHeader("Владелец").setWidth("5rem");

        List<CardEntityDto> cards = cardService.getCardsInfo();
        grid.setItems(cards);

        grid.addThemeVariants(
                GridVariant.LUMO_COLUMN_BORDERS,
                GridVariant.LUMO_ROW_STRIPES
        );

        setHeightFull();

        add(grid);
    }

}
