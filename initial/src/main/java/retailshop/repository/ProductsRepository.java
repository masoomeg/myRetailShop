package retailshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import retailshop.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Ma_Gorji on 2018/12/22.
 */



@Repository
    public interface ProductsRepository extends CrudRepository<Product, String>{

//    void save(Product product);
//    List<Product> getAllProducts();
//    Map<Long, Product> findAll();



}

