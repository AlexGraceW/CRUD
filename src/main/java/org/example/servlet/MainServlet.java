package org.example.servlet;

import org.example.config.AppConfig;
import org.example.controller.PostController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            if (method.equals("GET") && path.equals("/api/posts")) {
                controller.all(resp);
                return;
            }
            if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.getById(id, resp);
                return;
            }
            if (method.equals("POST") && path.equals("/api/posts")) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
