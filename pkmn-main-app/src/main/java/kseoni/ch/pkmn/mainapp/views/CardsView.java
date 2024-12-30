package kseoni.ch.pkmn.mainapp.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.PostConstruct;
import kseoni.ch.pkmn.apiclient.PokemonApiClient;
import kseoni.ch.pkmn.shared.dto.CardEntityDto;
import kseoni.ch.pkmn.core.services.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Route("cards")
@UIScope
@Service
@AnonymousAllowed
@RequiredArgsConstructor
public class CardsView extends VerticalLayout {

    private final CardService cardService;

    private final PokemonApiClient apiClient;

    @PostConstruct
    private void initUi(){
        log.info("Init UI");

        ComponentRenderer<Component, CardEntityDto> cardRenderer = new ComponentRenderer<>(
                person -> {
                    HorizontalLayout cardLayout = new HorizontalLayout();
                    cardLayout.setMargin(true);

                    Image image = new Image();
                    image.setSrc(apiClient.getImageFromApi(person.name(), person.cardNumber()));
                    image.setHeight("256px");
                    image.setHeight("128px");
                    image.addClickListener((ComponentEventListener<ClickEvent<Image>>) event -> {
                        Dialog dialog = new Dialog();
                        dialog.add(new Image(image.getSrc(), ""));
                        dialog.setCloseOnEsc(true);
                        dialog.open();
                    });

                    VerticalLayout infoLayout = new VerticalLayout();
                    infoLayout.setSpacing(false);
                    infoLayout.setPadding(false);
                    infoLayout.getElement().appendChild(
                            ElementFactory.createStrong(person.name()));
                    infoLayout.add(new Div(new Text(String.valueOf(person.hp()))));

                    VerticalLayout contactLayout = new VerticalLayout();
                    contactLayout.setSpacing(false);
                    contactLayout.setPadding(false);

                    String owner = Objects.isNull(person.pokemonOwner()) ? "None" : person.pokemonOwner().getFamilyName() + " " + person.pokemonOwner().getFirstName();

                    contactLayout.add(new Div(new Text(owner)));

                    String groupOwner = Objects.isNull(person.pokemonOwner()) ? "" : person.pokemonOwner().getGroup();

                    contactLayout
                            .add(new Div(new Text(groupOwner)));
                    infoLayout
                            .add(new Details("Владелец карты", contactLayout));

                    cardLayout.add(image, infoLayout);
                    return cardLayout;
                });


        setSizeFull();

        VirtualList<CardEntityDto> cards = new VirtualList<>();

        cards.setItems(cardService.getCardsInfo());
        cards.setRenderer(cardRenderer);

        log.info(cards.toString());

        add(new H1("Card list"));
        add(cards);

        log.info("components add: {}",this.getComponentCount());
    }

}

