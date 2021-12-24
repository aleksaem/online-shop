package service;

import dao.ProductDao;
import entity.Product;

import java.time.LocalDate;
import java.util.List;

public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findAll() {
        List<Product> products = productDao.findAll();
        System.out.println(products.size() + " products were found.");
        return products;
    }

    public void addProduct(Product product) {
        LocalDate creationDate = LocalDate.now();
        product.setCreationDate(creationDate);
        productDao.addProduct(product);
        System.out.println("Product: " + product.getName() + " was added!");
    }

    public Product findById(int id){
        Product neededProduct = productDao.findById(id);
        System.out.println("Needed product was found!");
        return neededProduct;
    }

    public void editProduct(Product product){
        productDao.editProduct(product);
        System.out.println("Product with id " + product.getId() + " was changed!");
    }

    public void deleteProduct(int id){
        System.out.println("Product with id " + id + " was deleted!");
        productDao.deleteProduct(id);
    }
}
