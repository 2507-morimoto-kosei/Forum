package com.example.forum.controller.form;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class CommentForm {
    private int id;
    private int content_id;
    private String comment;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;
}
