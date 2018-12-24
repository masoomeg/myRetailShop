package retailshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import retailshop.entity.Product;
import retailshop.repository.ProductsRepository;
import retailshop.repository.ProductsRepositoryImp;

import java.util.List;

/**
 * Created by Ma_Gorji on 2018/12/22.
 */

@RestController
public class ProductController {
    @Autowired
    ProductsRepositoryImp productsRepositoryImp;

    @RequestMapping(method = RequestMethod.GET, value = "/products")
    public List<Product> getProducts() {

        return productsRepositoryImp.getAllProducts();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/products/{id}")
    public Product getProduct(@PathVariable String id) {

        return productsRepositoryImp.getProductById(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/products/{id}")
    public void deleteProduct(@PathVariable String id) {


        productsRepositoryImp.delProductById(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/products")
    public void deleteAllProduct() {

        productsRepositoryImp.delAllProduct();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/products")
       public void saveProduct(@RequestBody Product product) {

           productsRepositoryImp.saveProduct(product);
       }

    @RequestMapping(method = RequestMethod.PUT, value = "/products/{id}")
           public Product updateProduct(@PathVariable String id,@RequestBody Product product) {

            return    productsRepositoryImp.updateProduct(product,id);
           }



}
