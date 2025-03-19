package org.example.repository;

import org.example.model.Post;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Post> all() {
        return posts.values().stream()
                .filter(post -> !post.isRemoved())
                .collect(Collectors.toList());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id)).filter(post -> !post.isRemoved());
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = counter.getAndIncrement();
            Post newPost = new Post(newId, post.getContent(), false);
            posts.put(newId, newPost);
            return newPost;
        } else {
            return posts.computeIfPresent(post.getId(), (id, existingPost) ->
                    existingPost.isRemoved() ? existingPost : new Post(id, post.getContent(), false));
        }
    }

    public void removeById(long id) {
        posts.computeIfPresent(id, (key, post) -> {
            post.setRemoved(true);
            return post;
        });
    }
}