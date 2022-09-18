package com.ben.commentfront.listener;

import com.ben.commentcommon.config.RabbitConfig;
import com.ben.commentcommon.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Setter
@Getter
@Component
public class CommentListener {

    private CountDownLatch countDownLatch;
    private CommentDto commentDto;
    private List<CommentDto> commentDtos;

    @RabbitListener(queues = RabbitConfig.SAVE_QUEUE_NAME)
    public void saveComment(CommentDto commentDto) {
        this.commentDto = commentDto;
        countDownLatch.countDown();
    }

    @RabbitListener(queues = RabbitConfig.FIND_QUEUE_NAME)
    public List<CommentDto> findComments() {
        countDownLatch.countDown();
        return commentDtos;
    }

}
