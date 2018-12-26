package retailshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import retailshop.domain.Product;

import javax.ws.rs.QueryParam;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select e from Product e where e.name like %:query%  ")
    public List<Product>  searchProduct(@Param("query" ) String query);

}
