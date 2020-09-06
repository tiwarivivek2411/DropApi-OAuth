package couture;
import com.google.common.collect.ImmutableList;
import couture.Brand;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
public class BrandRepository {
	    private final List<Brand> brands;
	 
	    public BrandRepository(List<Brand> brands) {
	        this.brands = ImmutableList.copyOf(brands);
	    }
	 
	    public List<Brand> findAll(int size) {
	        return brands.stream()
	          .limit(size)
	          .collect(Collectors.toList());
	    }
	 
	    public Optional<Brand> findById(Long id) {
	        return brands.stream()
	          .filter(brand -> brand.getId().equals(id))
	          .findFirst();
	    }
	}
