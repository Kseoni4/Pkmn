package kseoni.ch.pkmn.mainapp.views;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.RolesAllowed;
import kseoni.ch.pkmn.shared.dto.CardEntityDto;
import kseoni.ch.pkmn.core.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@UIScope
@Service
@Route("cards-grid")
@RolesAllowed("ROLE_ADMIN")
@RequiredArgsConstructor
public class CardsGridView extends VerticalLayout {

    private final CardService cardService;

    @PostConstruct
    private void initUi(){
        Grid<CardEntityDto> grid = new Grid<>(CardEntityDto.class, false);

        grid.addColumn(new ComponentRenderer<>(
                card -> {
                    String url = cardService.getCardImage(card.name(), card.cardNumber());

                    if(url.equals("None")){
                        return new Span("Изображение не найдено");
                    }

                    Image image = new Image();
                    image.setSrc(url);
                    image.setHeight("200px");
                    image.setWidth("150px");

                    return image;
                }
        )).setHeader("Картинка").setWidth("150px");
        grid.addColumn(CardEntityDto::name).setHeader("Имя").setWidth("5rem").setSortable(true);
        grid.addColumn(CardEntityDto::pokemonType).setHeader("Тип").setWidth("5rem");
        grid.addColumn(CardEntityDto::cardNumber).setHeader("Номер").setWidth("5rem");
        grid.addColumn(new ComponentRenderer<>(
                card -> {
                    Details ownerDetails = new Details();
                    ownerDetails.setOpened(false);
                    if(Objects.isNull(card.pokemonOwner())){
                        return new Span("Отсутствует");
                    }
                    ownerDetails.setSummaryText("Данные о владельце");
                    VerticalLayout ownerLayout = new VerticalLayout(
                            new Span(card.pokemonOwner().getFamilyName()),
                            new Span(card.pokemonOwner().getFirstName()),
                            new Span(card.pokemonOwner().getGroup())
                    );
                    ownerLayout.setSpacing(false);

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

        grid.addItemClickListener(
                 itemClickEvent -> {
                         CardEntityDto card = itemClickEvent.getItem();
                         Dialog cardDialog = createDialog(card);
                         cardDialog.open();
                 }
        );

        setHeightFull();

        add(grid);
    }


    private Dialog createDialog(CardEntityDto card){
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Данные о карте");

        TextField cardNameField = new TextField("Название", card.name(), "");
        cardNameField.setReadOnly(true);
        cardNameField.getStyle().set("padding-top", "0");

        TextField cardTypeField = new TextField("Тип карты", card.pokemonType().name(), "");
        cardTypeField.setReadOnly(true);

        TextField numberField = new TextField("Номер карты", card.cardNumber(), "");
        numberField.setReadOnly(true);

        TextField gameSetField = new TextField("Игровой выпуск", card.gameSet(), "");
        gameSetField.setReadOnly(true);

        Image cardImage = new Image(cardService.getCardImage(card.name(), card.cardNumber()), "");
        cardImage.setHeight("300px");
        cardImage.setWidth("250px");
        cardImage.addClickListener(event ->
        {
            Dialog imageDialog = new Dialog();
            imageDialog.add(new Image(cardImage.getSrc(), ""));
            imageDialog.open();
        });


        VerticalLayout imageLayout = new VerticalLayout(
            cardImage
        );

        VerticalLayout fieldLayout = new VerticalLayout(cardNameField,cardTypeField, numberField,
                gameSetField);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        HorizontalLayout dialogLayout = new HorizontalLayout(imageLayout, fieldLayout);
        dialogLayout.setSpacing(false);

        dialog.add(dialogLayout);

        return dialog;
    }
}