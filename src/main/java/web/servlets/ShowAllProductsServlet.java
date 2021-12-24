package web.servlets;

import entity.Product;
import service.ProductService;
import web.utils.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ShowAllProductsServlet extends HttpServlet {
    private ProductService productService;

    public ShowAllProductsServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        List<Product> products = productService.findAll();

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("products", products);

        String page = pageGenerator.getPage("products_list.html", parameters);
        resp.getWriter().write(page);
    }
}
