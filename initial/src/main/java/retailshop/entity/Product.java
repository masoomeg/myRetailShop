package retailshop.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * Created by Ma_Gorji on 2018/12/22.
 */
@RedisHash("Product")
public class Product  implements Serializable {

    private Long id;
    private String name;
    private String amount;
    private String description;

    private static final long serialVersionUID = 1L;
    public  Product(){

    }
    public Product(Long id,String name , String amount ){
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public Product(String name , String amount ){
           this.name = name;
           this.amount = amount;
       }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
