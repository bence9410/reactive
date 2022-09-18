package com.ben.commentfront;

import com.ben.commentcommon.dto.CommentDto;
import com.ben.commentfront.handler.ServerSentEventHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ServerSentEventHandlerUnitTests {

    private final ServerSentEventHandler serverSentEventHandler = new ServerSentEventHandler();
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private final Consumer<CommentDto> cons = c -> countDownLatch.countDown();

    @Test
    public void sseHandlerTest() throws InterruptedException {
        serverSentEventHandler.subscribe(cons);
        serverSentEventHandler.publish(CommentDto.builder().build());
        Assertions.assertTrue(countDownLatch.await(5, TimeUnit.SECONDS));
    }
}
