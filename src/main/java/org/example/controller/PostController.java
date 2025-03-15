package org.example.controller;

<<<<<<< HEAD
import com.google.gson.Gson;
import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;
    private final Gson gson = new Gson();
=======
import org.springframework.web.bind.annotation.*;
import org.example.model.Post;
import org.example.service.PostService;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;
>>>>>>> fc16aab (Ghanges added)

    public PostController(PostService service) {
        this.service = service;
    }

<<<<<<< HEAD
    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        try {
            var post = service.getById(id);
            response.getWriter().print(gson.toJson(post));
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(long id, HttpServletResponse response) {
        service.removeById(id);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
=======
    @GetMapping
    public List<Post> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public Post save(@RequestBody Post post) {
        return service.save(post);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id) {
        service.removeById(id);
    }
}
>>>>>>> fc16aab (Ghanges added)
