package web.servlets;

import entity.Product;
import service.ProductService;
import web.utils.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class EditProductServlet extends HttpServlet {
    private ProductService productService;

    public EditProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        int idOfEditingProduct = Integer.parseInt(req.getParameter("id"));
        Product editingProduct = productService.findById(idOfEditingProduct);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("product", editingProduct);
        String page = pageGenerator.getPage("product_edit.html", parameters);
        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            Double price = Double.parseDouble(req.getParameter("price"));
            String description = req.getParameter("description");
            Product editedProduct = Product.builder()
                    .id(id)
                    .name(name)
                    .price(price)
                    .description(description)
                    .build();

            productService.editProduct(editedProduct);
            resp.sendRedirect("/products");
        } catch (Exception e) {
            String errorMessage = "Product wasn`t edited! Check data and try again";
            String page = pageGenerator.getPageWithMessage("product_edit.html", errorMessage);

            resp.getWriter().write(page);
        }
    }
}
