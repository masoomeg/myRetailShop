package retailshop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retailshop.domain.Product;
import retailshop.service.ProductService;

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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @GetMapping(path={"/products"}, produces={MediaType.APPLICATION_JSON_VALUE })
        public ResponseEntity<?> searchProduct(@RequestParam(value ="name",required = false) String query) {

            return ResponseEntity.ok().body(productService.searchProducts(query));

        }


//    @GetMapping(path={"/products2"}, produces={MediaType.APPLICATION_JSON_VALUE })
//       public ResponseEntity<?> getProducts() {
//
//           return ResponseEntity.ok().body(productService.getProducts());
//
//       }

    @DeleteMapping(path={"/products/{id}"}, produces={MediaType.APPLICATION_JSON_VALUE })
          public void deleteProduct(@PathVariable(value ="id") Long id) {

              productService.deleteProducts(id);

          }

    @PostMapping(path={"/products"},consumes = {MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE })
           public ResponseEntity<?> saveProduct(@RequestBody(required = false) Product product) {

               return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProducts(product));

           }

    @PutMapping(path = {"/products/{id}"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateProduct(@PathVariable(value = "id") Long id, @RequestBody(required = false) Product product) {
        try {
           return  ResponseEntity.ok().body(productService.updateProducts(product,id));
        } catch (IllegalArgumentException e) {
          return   ResponseEntity.badRequest().build();
//          return   ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
