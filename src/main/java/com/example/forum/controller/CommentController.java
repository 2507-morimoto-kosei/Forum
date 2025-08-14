package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    /*
    返信をDBに登録するためServiceクラスへ流す処理
     */
    @PostMapping("/comment/{id}")
    public ModelAndView addContent(@PathVariable Integer id,
                                   @ModelAttribute("commentModel") CommentForm commentForm) {
        //コメントをテーブルへ格納するためServiceクラスへ流す
        commentForm.setContent_id(id);
        commentService.saveComment(commentForm);
        //先頭画面(ルート)にリダイレクト
        return new ModelAndView("redirect:/");
    }
}
