package com.ben.commentfront.service;

import com.ben.commentcommon.config.RabbitConfig;
import com.ben.commentcommon.dto.CommentDto;
import com.ben.commentfront.handler.ServerSentEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentFrontService {

    private static final ParameterizedTypeReference<List<CommentDto>> FIND_ALL_RESPONSE_TYPE =
            new ParameterizedTypeReference<>() {};

    private final RabbitTemplate rabbitTemplate;

    private final ServerSentEventHandler serverSentEventHandler;

    public void receive(CommentDto commentDto) {
        rabbitTemplate.convertAndSend(RabbitConfig.SAVE_QUEUE_NAME, commentDto);
        serverSentEventHandler.publish(commentDto);
    }

    public Flux<ServerSentEvent<CommentDto>> subscribe() {
        return Flux.<CommentDto>create(sink -> serverSentEventHandler.subscribe(sink::next))
                .map(comment -> ServerSentEvent.<CommentDto>builder().data(comment).build());
    }

    public List<CommentDto> findAllComment() {
        return rabbitTemplate.convertSendAndReceiveAsType(
                RabbitConfig.FIND_QUEUE_NAME, "", FIND_ALL_RESPONSE_TYPE);
    }
}
