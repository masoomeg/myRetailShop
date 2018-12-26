package retailshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retailshop.domain.Product;
import retailshop.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product findProductById(Long id) {
        return productRepository.findOne(id);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();

    }

    @Override
    public void deleteProducts(Long id) {
        productRepository.delete(id);
    }

    @Override
    public Product updateProducts(Product product, Long id) {

        if (product == null || product.getId()==null )
            throw new IllegalArgumentException("the product to update shouldent be null");

        Product p = productRepository.findOne(id);
        p.setName(product.getName());
        p.setAmount(product.getAmount());
        p.setDescription(product.getDescription());
        p.setCategory(product.getCategory());
        return productRepository.save(p);
    }

    @Override
    public Product saveProducts(Product product) throws Exception {
       if (product.getName()!=null && product.getName().length()>30)
           throw new Exception("length of name must be less than 30 characters!");
        return productRepository.save(product);
    }

    @Override
    public List<Product> searchProducts(String query) {

        if (query == null || query.isEmpty())
            return productRepository.findAll();
        else
            return productRepository.searchProduct(query);

    }


}
