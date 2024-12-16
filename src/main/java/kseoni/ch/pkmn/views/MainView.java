package kseoni.ch.pkmn.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;


@Route("/")
@UIScope
@Service
@RolesAllowed("ROLE_ADMIN")
public class MainView extends VerticalLayout implements BeforeEnterListener {

    public MainView(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.setAlignItems(Alignment.CENTER);

        Div div = new Div();

        div.add(new H1("Hello, "+user.getUsername()));

        Button button = new Button("Navigate to cards grid");
        button.addClickListener(e ->
                button.getUI().ifPresent(ui ->
                        ui.navigate("cards-grid"))
        );

        /*
            <div>
                 <button></button>
            </div

         */
        div.add(button);

        add(div);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
    }
}
