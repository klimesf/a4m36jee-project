package cz.cvut.fel.a4m36jee.airlines.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Custom session invalidate for logout.
 *
 * @author slavion3
 */
@ManagedBean(name = "userSession")
public class UserSessionController {

    public void logout() throws IOException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.sendRedirect("/airlines/logout");
    }

}
