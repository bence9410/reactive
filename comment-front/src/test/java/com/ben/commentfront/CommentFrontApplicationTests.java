package com.ben.commentfront;

import com.ben.commentcommon.dto.CommentDto;
import com.ben.commentfront.listener.CommentListener;
import com.ben.commentfront.service.CommentFrontService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class CommentFrontApplicationTests {

    @Autowired
    private CommentFrontService commentFrontService;

    @Autowired
    private CommentListener commentListener;

    @BeforeEach
    public void setUp() {
        commentListener.setCountDownLatch(new CountDownLatch(1));
    }

    @Test
    void receiveTest() throws InterruptedException {
        CommentDto expected = CommentDto.builder().content("asd").build();
        commentFrontService.receive(expected);

        commentListener.getCountDownLatch().await(5, TimeUnit.SECONDS);
        Assertions.assertEquals(expected, commentListener.getCommentDto());
    }

    @Test
    void findAllComment() throws InterruptedException {
        List<CommentDto> expected = List.of(CommentDto.builder().content("first").build(),
                CommentDto.builder().content("second").build());
        commentListener.setCommentDtos(expected);
        List<CommentDto> actual = commentFrontService.findAllComment();
        commentListener.getCountDownLatch().await(5, TimeUnit.SECONDS);
        Assertions.assertEquals(expected, actual);
    }
}