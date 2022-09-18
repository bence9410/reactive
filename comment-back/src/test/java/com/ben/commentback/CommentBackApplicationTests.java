package com.ben.commentback;

import com.ben.commentback.entity.Comment;
import com.ben.commentback.mapper.CommentMapper;
import com.ben.commentback.repository.CommentRepository;
import com.ben.commentcommon.config.RabbitConfig;
import com.ben.commentcommon.dto.CommentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
class CommentBackApplicationTests {

	private static final ParameterizedTypeReference<List<CommentDto>> FIND_ALL_RESPONSE_TYPE =
			new ParameterizedTypeReference<>() {};

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private RabbitConfig rabbitConfig;

	@Test
	void saveCommentTest() throws InterruptedException {
		CommentDto expected = new CommentDto("I really enjoyed this task.");
		List<Comment> expectedComments = commentRepository.findAll();

		rabbitTemplate.convertAndSend(RabbitConfig.SAVE_QUEUE_NAME, expected);

		List<Comment> actualComments = null;
		for (int i = 0; i < 10 && (Objects.isNull(actualComments) || actualComments.size() == 2); i++) {
			Thread.sleep(250);
			actualComments = commentRepository.findAll();
		}
		Assertions.assertEquals(4, actualComments.size());
		actualComments.removeAll(expectedComments);
		Assertions.assertEquals(1, actualComments.size());
		Assertions.assertEquals(expected.getContent(), actualComments.get(0).getContent());
	}

	@Test
	void findCommentsTests() {
		List<CommentDto> actual = rabbitTemplate.convertSendAndReceiveAsType(
				RabbitConfig.FIND_QUEUE_NAME, "", FIND_ALL_RESPONSE_TYPE);
		List<CommentDto> expected = commentRepository.findAll().stream().map(CommentMapper::toDto)
				.collect(Collectors.toList());
		Assertions.assertEquals(expected.size(), actual.size());
		Assertions.assertEquals(expected, actual);
	}
}