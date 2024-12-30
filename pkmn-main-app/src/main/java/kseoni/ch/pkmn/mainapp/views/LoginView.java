package kseoni.ch.pkmn.mainapp.views;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Value;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
@UIScope
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    public LoginView() {
        add(new H1("Pkmn Admin UI"));
        //Anchor loginLink = new Anchor(OAUTH_URL, "Login with Pkmn Auth");
        // Instruct Vaadin Router to ignore doing SPA handling
/*        loginLink.setRouterIgnore(true);
        add(loginLink);*/
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        /*if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }*/
    }
}