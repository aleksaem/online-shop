package dao;

import entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();

    Product findById(int id);

    void addProduct(Product product);

    void editProduct(Product product);

    void deleteProduct(int id);
}
