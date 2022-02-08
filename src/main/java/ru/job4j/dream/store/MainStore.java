package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;

public class MainStore {
    public static void main(String[] args) {
        Store store = DbStore.instOf();
        store.save(new Post(3, "Java Job", "Без опыта sss", LocalDateTime.now()));
        for (Post post : store.findAllPosts(false)) {
            System.out.println(post);
        }
        System.out.println(store.findPostById(3));
        store.save(new Candidate(2, "Стажер Java", "Roma", "Kolesov"));
        for (Candidate candidate : store.findAllCandidates(false)) {
            System.out.println(candidate);
        }
        System.out.println(store.findCandidateById(2));
    }
}
