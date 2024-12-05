package kz.goldenfish.goldenfish.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.StreamUtils;

@RestController
public class FaviconController {

    @GetMapping("favicon.ico")
    public void favicon(HttpServletResponse response) {
        try (InputStream stream = getClass().getResourceAsStream("/static/favicon.ico")) {
            response.setContentType("image/x-icon");
            IOUtils.copy(stream, response.getOutputStream());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}