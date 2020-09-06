package couture;
import couture.ApplicationHealthCheck;
import couture.DropApiConfiguration;
import couture.Brand;
import couture.BrandRepository;
import couture.BrandResource;
//import couture.SimpleAuthenticator;
import io.dropwizard.Application;
import couture.AppAuthorizer;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import java.util.ArrayList;
import java.util.List;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.auth.AuthDynamicFeature;
//import couture.SimpleAuthenticator;
//import couture.ApplicationUser;
import couture.OAuthAuthenticator;
import couture.User;
import couture.AccessTokenDao;
import couture.UserDao;
import couture.OAuth2Resource;
import org.joda.time.DateTimeZone;
import java.util.*;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import io.dropwizard.ConfiguredBundle;
import java.util.logging.Logger;
import org.glassfish.jersey.logging.LoggingFeature;
public class DropApiApplication extends Application<DropApiConfiguration> {
    public static void main(final String[] args) throws Exception {
        new DropApiApplication().run("server","config.yml");
    }

    @Override
    public String getName() {
        return "DropApi";
    }
	
    @Override
    public void initialize(final Bootstrap<DropApiConfiguration> bootstrap) {
        // TODO: application initialization
		bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
	        super.initialize(bootstrap);
//        bootstrap.addBundle(new WebLoggerBundle);
	    DateTimeZone.setDefault(DateTimeZone.UTC);

    }

    @Override
    public void run(final DropApiConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
		final int defaultSize = configuration.getDefaultSize();
	    final BrandRepository brandRepository = new BrandRepository(initBrands());
	    final BrandResource brandResource = new BrandResource(defaultSize, brandRepository);
	    System.out.println("hi2");
	 	AccessTokenDao atd=new AccessTokenDao();
		UserDao ud=new UserDao();

	    environment
	      .jersey()
	      .register(brandResource);
		environment.jersey().register(new OAuth2Resource(atd,ud));
        final ApplicationHealthCheck healthCheck = new ApplicationHealthCheck();
        // if(configuration.isWebLoggerEnabled()){
            
        // }
//        environment.jersey().register(new LoggingFeature(Logger.getLogger(getClass().getName()),Level.INFO,LoggingFeature.Verbosity>PAYLOAD_ANY));
        environment
          .healthChecks()
          .register("application", healthCheck);
		environment.jersey().register(new AuthDynamicFeature(new OAuthCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new OAuthAuthenticator(atd,ud))
				.setAuthorizer(new AppAuthorizer())
                .setPrefix("Bearer")
                .buildAuthFilter()));
	environment.jersey().register(RolesAllowedDynamicFeature.class);
	environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

    }
 private List<Brand> initBrands() {
        final List<Brand> brands = new ArrayList<>();
        brands.add(new Brand(1L, "Brand1"));
        brands.add(new Brand(2L, "Brand2"));
        brands.add(new Brand(3L, "Brand3"));

        return brands;
    }

}
