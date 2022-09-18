package com.ben.commentback;

import com.ben.commentback.entity.Comment;
import com.ben.commentback.repository.CommentRepository;
import com.ben.commentcommon.config.RabbitConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(RabbitConfig.class)
@SpringBootApplication
public class CommentBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentBackApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(CommentRepository commentRepository) {
        return args -> {
            commentRepository.saveAll(List.of(
                    Comment.builder().content("first").build(),
                    Comment.builder().content("second").build(),
                    Comment.builder().content("third").build()));
        };
    }
}
