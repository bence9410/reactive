package com.ben.commentback.listener;

import com.ben.commentback.entity.Comment;
import com.ben.commentback.mapper.CommentMapper;
import com.ben.commentback.repository.CommentRepository;
import com.ben.commentcommon.config.RabbitConfig;
import com.ben.commentcommon.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentListener {

    private final CommentRepository commentRepository;

    @RabbitListener(queues = RabbitConfig.SAVE_QUEUE_NAME)
    public void saveComment(CommentDto commentDto) {
        Comment comment = commentRepository.save(CommentMapper.toEntity(commentDto));
        log.info("Saved comment: " + comment);
    }

    @RabbitListener(queues = RabbitConfig.FIND_QUEUE_NAME)
    public List<CommentDto> findComments() {
        List<Comment> result = commentRepository.findAll();
        log.info("Found comments: " + result);
        return result.stream().map(CommentMapper::toDto).collect(Collectors.toList());
    }

}
