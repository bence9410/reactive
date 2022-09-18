package com.ben.commenttest;

import com.ben.commentcommon.dto.CommentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CommentTestApplicationTests {

	private static final ParameterizedTypeReference<List<CommentDto>> FIND_ALL_RESPONSE_TYPE =
			new ParameterizedTypeReference<>() {};

	private static final ParameterizedTypeReference<ServerSentEvent<CommentDto>> SUBSCRIBE_RESPONSE_TYPE =
			new ParameterizedTypeReference<>() {};

	@Test
	void addCommentTest() throws InterruptedException {
		RestTemplate restTemplate = new RestTemplate();
		List<CommentDto> resp = restTemplate.exchange("http://localhost:8080/comments",
				HttpMethod.GET, HttpEntity.EMPTY, FIND_ALL_RESPONSE_TYPE).getBody();
		Assertions.assertEquals(3, resp.size());
		Assertions.assertTrue(resp.get(0).getContent().contains("first"));
		Assertions.assertTrue(resp.get(1).getContent().contains("second"));
		Assertions.assertTrue(resp.get(2).getContent().contains("third"));

		WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
		Flux<ServerSentEvent<CommentDto>> sse = webClient.get()
				.uri("/comments/subscribe")
				.retrieve()
				.bodyToFlux(SUBSCRIBE_RESPONSE_TYPE);

		CountDownLatch cdl = new CountDownLatch(1);
		sse.subscribe(ctx -> cdl.countDown());
		Thread.sleep(1000);

		restTemplate.exchange("http://localhost:8080/comments",
				HttpMethod.POST, new HttpEntity<>(CommentDto.builder()
						.content("fourth").build()), FIND_ALL_RESPONSE_TYPE).getBody();

		Assertions.assertTrue(cdl.await(10, TimeUnit.SECONDS));
		//TODO delete added comment, so test can run again
	}
}