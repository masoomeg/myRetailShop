import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import retailshop.Application;
import retailshop.controller.ProductResource;
import retailshop.domain.Product;
import retailshop.repository.ProductRepository;
import retailshop.service.ProductService;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProductControllerIntTest {

    private static final String NAME = "testName";
    private static final String DESCRIPTION = "testDesc";
    private static final String CATEGORY = "testCategory";
    private static final String AMOUNT = "1111";

    private static final String UPDATE_NAME = "testName_updated";
    private static final String UPDATED_ESCRIPTION = "testDesc_updated";
    private static final String UPDATE_CATEGORY = "testCategory_updated";
    private static final String UPDATE_AMOUNT = "2222";

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private EntityManager em;
    private MockMvc restProductMockMvc;
    private Product product;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductResource productResource = new ProductResource(productService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    public static Product createEntity(EntityManager em) {
        Product product = new Product();
        product.setName(NAME);
        product.setDescription(DESCRIPTION);
        product.setCategory(CATEGORY);
        product.setAmount(AMOUNT);
        return product;
    }

    @Before
    public void initTest() {
        product = createEntity(em);
    }

    public static byte[] convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        return mapper.writeValueAsBytes(object);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        restProductMockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(product)))
                .andExpect(status().isCreated());

        List<Product> productsList = productRepository.findAll();
        productsList.stream().forEach(p -> System.out.println(p.getId() + "- " + p.getName()));
        assertThat(productsList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productsList.get(productsList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(NAME);
        assertThat(testProduct.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(testProduct.getCategory()).isEqualTo(CATEGORY);
        assertThat(testProduct.getAmount()).isEqualTo(AMOUNT);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        product.setId(-1L);

        restProductMockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(product)))
                .andExpect(status().isCreated());

        List<Product> productsList = productRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        productRepository.save(product);
        restProductMockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].name").value(hasItem(NAME.toString())));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        productRepository.saveAndFlush(product);

        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.description").value(DESCRIPTION))
                .andExpect(jsonPath("$.category").value(CATEGORY))
                .andExpect(jsonPath("$.amount").value(AMOUNT));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        productRepository.save(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        Product updatedProduct = productRepository.findOne(product.getId());
        updatedProduct.setName(UPDATE_NAME);
        updatedProduct.setDescription(UPDATED_ESCRIPTION);
        updatedProduct.setCategory(UPDATE_CATEGORY);
        updatedProduct.setAmount(UPDATE_AMOUNT);

        restProductMockMvc.perform(put("/api/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(updatedProduct)))
                .andExpect(status().isOk());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATE_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_ESCRIPTION);
        assertThat(testProduct.getCategory()).isEqualTo(UPDATE_CATEGORY);
        assertThat(testProduct.getAmount()).isEqualTo(UPDATE_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        restProductMockMvc.perform(put("/api/products/{id}", 500)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(product)))
                .andExpect(status().isBadRequest());

//        List<Product> list = productRepository.findAll();
//        assertThat(list).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        productRepository.save(product);
        int databaseSizeBeforeDelete = productRepository.findAll().size();

        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> list = productRepository.findAll();
        assertThat(list).hasSize(databaseSizeBeforeDelete - 1);
    }


}
