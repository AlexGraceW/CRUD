package org.example.service;

import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(() -> new NotFoundException("Post not found"));
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}
        return repository.getById(id)
                .orElseThrow(() -> new NotFoundException("Post not found"));
    }

    public Post save(Post post) {
        Post existingPost = repository.getById(post.getId()).orElse(null);
        if (existingPost != null && existingPost.isRemoved()) {
            throw new NotFoundException("Cannot update deleted post");
        }
        Post savedPost = repository.save(post);
        if (savedPost == null) {
            throw new NotFoundException("Post not found");
        }
        return savedPost;
    }

    public void removeById(long id) {
        if (repository.getById(id).isEmpty()) {
            throw new NotFoundException("Post not found");
        }
        repository.removeById(id);
    }
}
