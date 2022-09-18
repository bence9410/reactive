package com.ben.commentback.mapper;

import com.ben.commentback.entity.Comment;
import com.ben.commentcommon.dto.CommentDto;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getContent());
    }

    public static Comment toEntity(CommentDto commentDto) {
        return Comment.builder().content(commentDto.getContent()).build();
    }
}