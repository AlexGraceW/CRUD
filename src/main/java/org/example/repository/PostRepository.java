package org.example.repository;

import org.example.model.Post;
<<<<<<< HEAD
=======
import org.springframework.stereotype.Repository;
>>>>>>> fc16aab (Ghanges added)

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Post> all() {
        return List.copyOf(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = counter.getAndIncrement();
            Post newPost = new Post(newId, post.getContent());
            posts.put(newId, newPost);
            return newPost;
        } else {
            return posts.computeIfPresent(post.getId(), (id, existingPost) ->
                    new Post(id, post.getContent()));
        }
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}