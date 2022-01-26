package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {
    private static final MemStore INST = new MemStore();
    private static final AtomicInteger POST_ID = new AtomicInteger(3);
    private static final AtomicInteger CANDIDATES_ID = new AtomicInteger(3);
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

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

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public Post findPostById(int id) {
        return posts.get(id);
    }

    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATES_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    public void deleteCandidate(int id) {
        candidates.remove(id);
    }
}
