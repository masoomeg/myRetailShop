package retailshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Product> getProducts() {

        return productsRepositoryImp.getAllProducts();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/products/{id}")
    public Product getProduct(@PathVariable String id) {

        return productsRepositoryImp.getProductById(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {


        try {
            productsRepositoryImp.delProductById(id);
        }catch (Exception e ){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/products")
    public void deleteAllProduct() {

        productsRepositoryImp.delAllProduct();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/products" ,produces = {"application/json"})
       public Product saveProduct(@RequestBody Product product) {

           return productsRepositoryImp.saveProduct(product);
       }

    @RequestMapping(method = RequestMethod.PUT, value = "/products/{id}")
           public void updateProduct(@PathVariable String id,@RequestBody Product product) {

               productsRepositoryImp.updateProduct(product,id);
           }



}
