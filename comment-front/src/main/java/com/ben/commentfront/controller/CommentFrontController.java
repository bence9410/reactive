package com.ben.commentfront.controller;

import com.ben.commentcommon.dto.CommentDto;
import com.ben.commentfront.service.CommentFrontService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentFrontController {

    private final CommentFrontService commentReceiverService;

    @PostMapping
    public void receive(@RequestBody CommentDto commentDto){
        commentReceiverService.receive(commentDto);
    }

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<CommentDto>> subscribe(){
        return commentReceiverService.subscribe();
    }

    @GetMapping
    public List<CommentDto> findAllComment() {
        return commentReceiverService.findAllComment();
    }

}
