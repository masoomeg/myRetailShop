package retailshop.service;


import retailshop.domain.Product;

import java.util.List;

public interface ProductService {

    public Product findProductById(Long id);
    public List<Product> getProducts();
    public void deleteProducts(Long id);
    public Product updateProducts(Product product, Long id);
    public Product saveProducts(Product product) throws Exception;
    public List<Product> searchProducts(String query);
}
