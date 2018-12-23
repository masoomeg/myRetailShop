package retailshop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import retailshop.config.RedisConfig;
import retailshop.entity.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Ma_Gorji on 2018/12/22.
 */
@Component
public class ProductsRepositoryImp {
    private static final String KEY = "Product";
     @Autowired
     ProductsRepository productsRepository;
      @Autowired
    retailshop.config.RedisAtomicLong redisAtomicLong;

//    private RedisTemplate<String, Object> redisTemplate;
//   	private HashOperations<String, Long, Product> hashOperations;
//
//    @Autowired
//   	public ProductsRepositoryImp(RedisTemplate<String, Object> redisTemplate) {
//   		this.redisTemplate = redisTemplate;
//   	}
//
//   	@PostConstruct
//   	private void init() {
//   		hashOperations = redisTemplate.opsForHash();
//   	}
//
//
//    @Override
//   	public void save(Product product) {
//   		hashOperations.put(KEY, product.getId(), product);
//   	}
//
    public  Product save(Product s) {



        return productsRepository.save(new Product(redisAtomicLong.getRedisAtomicLong().incrementAndGet(),"test","1666"));
    }

//
//    @Override
//   	public Map<Long, Product> findAll() {
//   		return hashOperations.entries(KEY);
//   	}


    public  Product getProductById(String id) {

            return productsRepository.findById(id)==null ? null : productsRepository.findById(id).get();
        }

    public  void delProductById(String id) {
        System.out.println(id);
             productsRepository.deleteById(id);
        }

    public  void delAllProduct() {
                 productsRepository.deleteAll();
            }

    public  Product saveProduct(Product product) {

        product.setId(redisAtomicLong.getRedisAtomicLong().incrementAndGet());
                 return    productsRepository.save(product);
                }


    public  void updateProduct(Product product,String id) {

        Product p=productsRepository.findById(id).get();
        p.setName(product.getName());
        p.setAmount(product.getAmount());
        p.setDescription(product.getDescription());
                         productsRepository.save(p);
                    }


   public List<Product> getAllProducts(){
//       this.save(new Product("test","33"));
       List<Product> products = new ArrayList<>();
//       Map<Long, Product> ps=  findAll();
//       for (Product product : ps.values()) {
//     			products.add(product);
//     		}
       productsRepository.findAll().forEach(products::add);

       return products;
   }

}
