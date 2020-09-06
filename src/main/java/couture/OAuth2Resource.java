package couture;
import static javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import couture.AccessToken;
import couture.User;
import couture.AccessTokenDao;
import couture.UserDao;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import io.dropwizard.auth.Auth;
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)

public class
OAuth2Resource {

    private final AccessTokenDao accessTokenDAO;
    private final UserDao userDAO;
	public OAuth2Resource(AccessTokenDao atd, UserDao ud){
		this.accessTokenDAO=atd;
		this.userDAO=ud;
	}
    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String getToken( @FormParam("username") String username,
        @FormParam("password") String password) {
        Optional<User> user = userDAO.findUserByUsernameAndPassword(username, password);
        if (!user.isPresent()) {
            throw new WebApplicationException(Response.status(UNAUTHORIZED).build());
        }

        AccessToken accessToken = accessTokenDAO.generateNewAccessToken(user.get(), new DateTime());
        return accessToken.getAccessTokenId().toString();
    }
}
