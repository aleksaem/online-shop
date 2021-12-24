package web;

import dao.jdbc.JdbcProductDao;
import org.eclipse.jetty.server.Server;
import service.ProductService;
import web.servlets.AddProductServlet;
import web.servlets.DeleteProductServlet;
import web.servlets.EditProductServlet;
import web.servlets.ShowAllProductsServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class Starter {
    public static void main(String[] args) throws Exception {
        JdbcProductDao jdbcProductDao = new JdbcProductDao();

        ProductService productService = new ProductService(jdbcProductDao);

        ShowAllProductsServlet showAllProductsServlet = new ShowAllProductsServlet(productService);
        AddProductServlet addProductServlet = new AddProductServlet(productService);
        EditProductServlet editProductServlet = new EditProductServlet(productService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        contextHandler.addServlet(new ServletHolder(showAllProductsServlet), "/products");
        contextHandler.addServlet(new ServletHolder(addProductServlet), "/products/add");
        contextHandler.addServlet(new ServletHolder(editProductServlet), "/products/edit");
        contextHandler.addServlet(new ServletHolder(deleteProductServlet), "/products/delete");

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        server.start();
    }
}
