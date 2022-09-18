package com.ben.commentfront;

import com.ben.commentcommon.config.RabbitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(RabbitConfig.class)
@SpringBootApplication
public class CommentFrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentFrontApplication.class, args);
	}

}
