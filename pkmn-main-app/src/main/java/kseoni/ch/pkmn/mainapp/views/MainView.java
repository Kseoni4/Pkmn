package kseoni.ch.pkmn.mainapp.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;


@Route("/")
@UIScope
@Service
@PermitAll
public class MainView extends VerticalLayout implements BeforeEnterListener {

    private static final String LOGOUT_SUCCESS_URL = "/logout";

    public MainView(){

        OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Div div = new Div();

        div.add(new H1("Hello, "+user.getName()));

        this.setAlignItems(Alignment.CENTER);

        Button button = new Button("Navigate to cards grid");
        button.addClickListener(e ->
                button.getUI().ifPresent(ui ->
                        ui.navigate("cards-grid"))
        );
        div.add(button);

        Button logoutButton = new Button("Logout", click -> {
            UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
/*            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(
                    VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                    null);*/
        });

        div.add(logoutButton);

        add(div);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
    }
}
