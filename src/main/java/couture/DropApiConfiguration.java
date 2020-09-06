package couture;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.NotNull;
import java.util.List;
public class DropApiConfiguration extends Configuration{
    // TODO: implement service configuration
	@NotNull 
	private int defaultSize;

    @JsonProperty
    public String apiUsername;

	@JsonProperty
	private List<String> allowedGrantTypes;

    @JsonProperty
    public String apiPassword;

	
    @JsonProperty
    private String bearerRealm;
//    private final boolean webLoggerEnabled;
//
//    @JsonCreator public DropApiCOnfiguration(@JsonProperty("webLoggerEnabled") boolean webLoggerEnabled){
//        this.webLoggerEnabled=webLoggerEnabled;
//    }
 
    public int getDefaultSize() {
        return defaultSize;
    }
	public String getApiUsername() {
        return apiUsername;
    }

    public void setApiUsername(String apiUsername) {
        this.apiUsername = apiUsername;
    }

    public String getApiPassword() {
        return apiPassword;
    }

    public void setApiPassword(String apiPassword) {
        this.apiPassword = apiPassword;
    }
	public void setBearerRealm(String bearerRealm) {
        this.bearerRealm = bearerRealm;
    }
	public String getBearerRealm(){
		return this.bearerRealm;	
	}
	public List<String> getAllowedGrantTypes(){
		return allowedGrantTypes;
	}


//   @Override
//   public boolean isWebLoggerEnabled() {
//       return this.webLoggerEnabled;
//   }
}
