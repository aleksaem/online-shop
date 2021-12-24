package dao.jdbc;

import dao.ProductDao;
import dao.jdbc.mapper.ProductRowMapper;
import entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private static final String SELECT_ALL_SQL = "SELECT id, name, price, description, date FROM Products";
    private static final String FIND_BY_ID_SQL = "SELECT id, name, price, description, date FROM Products WHERE id = ?";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE Products SET name = ?, price = ?, description = ? WHERE id = ?";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM Products WHERE id = ?";
    private static final String ADD_PRODUCT_SQL = """
            INSERT INTO products (name, price, description, date) VALUES (?, ?, ?, ?)
            """;

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                products.add(PRODUCT_ROW_MAPPER.mapRow(resultSet));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public Product findById(int id) {
        Product neededProduct = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    neededProduct = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Product with id " + id + " wasn`t found!");
        }
        return neededProduct;
    }

    @Override
    public void addProduct(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_SQL)) {
            if (product.getPrice() < 0) {
                throw new RuntimeException();
            }
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setDate(4, Date.valueOf(product.getCreationDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Products was not added!");
        }
    }

    @Override
    public void editProduct(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {
            if (product.getPrice() < 0) {
                throw new RuntimeException();
            }
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Product wasnt` updated!" + e);
        }
    }

    @Override
    public void deleteProduct(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Product wasn`t deleted!");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/online-shop", "postgres", "pswd");
    }
}
