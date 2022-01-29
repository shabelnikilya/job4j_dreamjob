package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final MemStore INST = new MemStore();
    private static final AtomicInteger POST_ID = new AtomicInteger(3);
    private static final AtomicInteger CANDIDATES_ID = new AtomicInteger(3);
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job",
                "Требуется младший разработчик без коммерческого опыта", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job",
                "Требуется Java разработчик с опытом от 1 года", LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job",
                "Требуется Java разработчик с опытом от 3 лет", LocalDateTime.now()));
        candidates.put(1, new Candidate(1, "Junior Java", "Ilya", "Kolosov"));
        candidates.put(2, new Candidate(2, "Middle Java", "Roma", "Ivanov"));
        candidates.put(3, new Candidate(3, "Senior Java", "Jora", "Remuzov"));
    }

    public static MemStore instOf() {
        return INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public Collection<User> findAllUsers() {
        return users.values();
    }

    @Override
    public Post findPostById(int id) {
        return posts.get(id);
    }

    @Override
    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    @Override
    public User findUserById(int id) {
        return users.get(id);
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATES_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            user.setId(CANDIDATES_ID.incrementAndGet());
        }
        users.put(user.getId(), user);
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<User> rsl = users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        return rsl.orElse(null);
    }

    @Override
    public void deleteCandidate(int id) {
        candidates.remove(id);
    }
}
