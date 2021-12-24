package web.utils;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

public class PageGenerator {
    private static final String HTML_DIR = "templates";

    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    private PageGenerator() {
        cfg = new Configuration();
    }

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File(HTML_DIR));
            cfg.setTemplateLoader(fileTemplateLoader);
            Template template = cfg.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }

    public String getPage(String filename) {
        return getPage(filename, Collections.emptyMap());
    }

    public String getPageWithMessage(String fileName, String message) {
        Map<String, Object> parameters = Map.of("errorMessage", message);
        return getPage(fileName, parameters);
    }

}
