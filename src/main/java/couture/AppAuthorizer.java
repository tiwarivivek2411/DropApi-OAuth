package couture;
 
import io.dropwizard.auth.Authorizer;
 
public class AppAuthorizer implements Authorizer<User> 
{
    @Override
    public boolean authorize(User user, String role) {
        return user.getRoles() != null && user.getRoles().equals(role);
    }
}
