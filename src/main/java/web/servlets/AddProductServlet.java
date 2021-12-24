package web.servlets;

import entity.Product;
import service.ProductService;
import web.utils.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {
    private ProductService productService;

    public AddProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("product_add.html");
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        try {
            String name = req.getParameter("name");
            Double price = Double.parseDouble(req.getParameter("price"));
            String description = req.getParameter("description");
            Product newProduct = Product.builder()
                    .name(name)
                    .price(price)
                    .description(description)
                    .build();

            productService.addProduct(newProduct);
            resp.sendRedirect("/products");
        } catch (Exception e) {
            String errorMessage = "Product wasn`t added! Check entered data and try again.";
            String page = pageGenerator.getPageWithMessage("product_add.html", errorMessage);

            resp.getWriter().write(page);
        }
    }
}
