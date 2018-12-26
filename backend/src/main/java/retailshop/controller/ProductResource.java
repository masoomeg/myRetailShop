package retailshop.controller;


import org.h2.jdbc.JdbcSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retailshop.domain.Product;
import retailshop.service.Error;
import retailshop.service.ProductService;

import java.sql.SQLDataException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api")
public class ProductResource {

    public static final String CHARSET_UTF8 = "; charset=UTF-8";

    ProductService productService;

    @Autowired
    public  ProductResource(ProductService productService){

        this.productService = productService;
    }

    @GetMapping(path={"/products/{id}"},  produces={MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getProduct(@PathVariable(value ="id") Long id) {

        Product p = productService.findProductById(id);
        if (p != null) {
            return ResponseEntity.ok().body(productService.findProductById(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(HttpStatus.NOT_FOUND + "", "product not found!"));
        }

    }

    @GetMapping(path={"/products"}, produces={MediaType.APPLICATION_JSON_VALUE })
        public ResponseEntity<?> searchProduct(@RequestParam(value ="name",required = false) String query) {

            return ResponseEntity.ok().body(productService.searchProducts(query));

        }


    @DeleteMapping(path={"/products/{id}"}, produces={MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long id) {
        try {
            productService.deleteProducts(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(HttpStatus.BAD_REQUEST + "", "EmptyResultDataAccessException: product with this id is not exist!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(HttpStatus.BAD_REQUEST + "", e.getMessage()));
        }

    }

    @PostMapping(path={"/products"},consumes = {MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> saveProduct(@RequestBody(required = false) Product product) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProducts(product));
        } catch (DataIntegrityViolationException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(HttpStatus.BAD_REQUEST + "", "DataIntegrityViolationException: id and name must be unique!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(HttpStatus.BAD_REQUEST + "", e.getMessage()));
        }

    }

    @PutMapping(path = {"/products/{id}"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateProduct(@PathVariable(value = "id") Long id, @RequestBody(required = false) Product product) {
        try {
            return ResponseEntity.ok().body(productService.updateProducts(product, id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(HttpStatus.NOT_FOUND + "", "id to update is not exist!"));
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(HttpStatus.BAD_REQUEST + "", e.getMessage()));
        }
    }

}
