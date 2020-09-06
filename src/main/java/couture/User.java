package couture;
import java.security.Principal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Data
@AllArgsConstructor
public class User implements Principal {
	private Long id;
	private String username;
	private String password;
	private String role;

	public String getName() {
		return username;
	}
	public Long getUserId() {
		return id;
	}

	public String getRoles(){
		return role;

	}
}
