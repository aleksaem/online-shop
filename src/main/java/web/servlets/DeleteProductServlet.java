package web.servlets;

import entity.Product;
import service.ProductService;
import web.utils.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class DeleteProductServlet extends HttpServlet {
    private ProductService productService;

    public DeleteProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        try {
            int id = Integer.parseInt(req.getParameter("id"));

            productService.deleteProduct(id);
            resp.sendRedirect(req.getContextPath() + "/products");
        } catch (Exception e) {
            String errorMessage = "Product wasn`t deleted! Check id and try again";
            String page = pageGenerator.getPageWithMessage("products_list.html", errorMessage);

            resp.getWriter().write(page);
        }
    }
}
