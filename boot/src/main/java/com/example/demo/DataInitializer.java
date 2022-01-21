package com.example.demo;

import com.example.demo.domain.model.Post;
import com.example.demo.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("dev")
public class DataInitializer {

    private final PostRepository posts;

    @EventListener(value = ApplicationReadyEvent.class)
    public void init() throws Exception {
        log.info("start data initialization...");
        Stream.of("one", "two")
                .map(s -> Post.builder().title("Post " + s).content("Content of post " + s).build())
                .forEach(this.posts::save);
        this.posts.findAll().forEach(p -> log.debug("saved post:{}", p));
    }
}