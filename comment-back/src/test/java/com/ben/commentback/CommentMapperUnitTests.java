package com.ben.commentback;

import com.ben.commentback.entity.Comment;
import com.ben.commentback.mapper.CommentMapper;
import com.ben.commentcommon.dto.CommentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommentMapperUnitTests {

    @Test
    public void toDtoTest(){
        Comment comment = Comment.builder().content("test1").build();
        CommentDto commentDto = CommentMapper.toDto(comment);
        Assertions.assertEquals(comment.getContent(), commentDto.getContent());
    }

    @Test
    public void toEntityTest(){
        CommentDto commentDto = CommentDto.builder().content("test2").build();
        Comment comment = CommentMapper.toEntity(commentDto);
        Assertions.assertEquals(commentDto.getContent(), comment.getContent());
    }
}
