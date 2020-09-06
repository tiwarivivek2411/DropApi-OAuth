package couture;
import couture.AccessToken;
import couture.User;
import couture.AccessTokenDao;
import couture.UserDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Period;
import java.io.*;
import java.util.UUID;
import java.util.Base64;
//@AllArgsConstructor
public class OAuthAuthenticator implements Authenticator<String, User> {
	public static final int ACCESS_TOKEN_EXPIRE_TIME_MIN = 2;
	private final AccessTokenDao accessTokenDAO;
	private final UserDao userDAO;
	public OAuthAuthenticator(AccessTokenDao atd, UserDao ud){
		this.accessTokenDAO=atd;
		this.userDAO=ud;
	}
	@Override
	public Optional<User> authenticate(String accessTokenId) throws AuthenticationException {
		
		UUID accessTokenUUID;
		try {
 			accessTokenUUID = UUID.fromString(accessTokenId
			);
		} catch (IllegalArgumentException e) {
			boolean var=false;
			return Optional.empty();
		}

		Optional<AccessToken> accessToken = accessTokenDAO.findAccessTokenById(accessTokenUUID);
		if (!accessToken.isPresent()) {
			boolean var=false;
			return Optional.empty();
		}
		Period period = new Period(accessToken.get().getLastAccessUTC(), new DateTime());
		if (period.getMinutes() > ACCESS_TOKEN_EXPIRE_TIME_MIN) {
			return Optional.empty();
		}
	

		accessTokenDAO.setLastAccessTime(accessTokenUUID, new DateTime());

		return userDAO.findUserById(accessToken.get().getUserId());
	}
}
