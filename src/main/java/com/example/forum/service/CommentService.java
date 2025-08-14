package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    /*
    投稿内容をDBに追加処理
     */
    public void saveComment(CommentForm reqComment) {
        //Form→Entityに詰め替えられたを変数に保持
        Comment saveComment = setCommentEntity(reqComment);
        //saveはjpaRepositoryの便利メソッド(INSERT処理)
        commentRepository.save(saveComment);
    }
    /*
    Form→Entityに詰替え処理(追加処理用)
     */
    //Reportは@Entityされてたね
    private Comment setCommentEntity(CommentForm reqComment) {
        //詰替え先のEntityオブジェクトを生成
        Comment comment = new Comment();
        comment.setId(reqComment.getId());
        comment.setContent_id(reqComment.getContent_id());
        comment.setComment(reqComment.getComment());
        return comment;
    }
}
