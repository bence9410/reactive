package com.ben.commentfront.handler;

import com.ben.commentcommon.dto.CommentDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Component
public class ServerSentEventHandler {

    private final List<Consumer<CommentDto>> listeners = new CopyOnWriteArrayList<>();

    public void subscribe(Consumer<CommentDto> listener) {
        listeners.add(listener);
    }

    public void publish(CommentDto comment) {
        listeners.forEach(listener -> listener.accept(comment));
    }

}
