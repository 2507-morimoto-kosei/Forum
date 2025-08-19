package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.entity.Report;
import com.example.forum.service.CommentService;
import com.example.forum.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    ReportService reportService;

    /*
    返信をDBに登録するためServiceクラスへ流す処理
     */
    @PostMapping("/comment/{content_id}")
    public ModelAndView addContent(@PathVariable Integer content_id,
            @ModelAttribute("commentModel") CommentForm commentForm) {
        //コメントをDBに登録する
        commentService.saveComment(commentForm);

        //contentIDを引数に投稿の更新日時を変更するメソッドを起動
        reportService.updateDateReport(content_id);

        //先頭画面(ルート)にリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
    コメント編集画面を表示するための処理
     */
    //GETリクエストを受付、idを動的に受取る
    @GetMapping("/commentEdit/{id}")
    public ModelAndView editComment(@PathVariable Integer id) {
        //ModelAndViewはviewに渡すデータと渡し先を管理するオブジェクト
        ModelAndView mav = new ModelAndView();
        //受取ったidを元に編集するコメントをDBから取得
        CommentForm comment = commentService.editComment(id);
        //DBから取得した編集対象のコメントをセット
        mav.addObject("commentModel", comment);
        //画面遷移先を指定
        mav.setViewName("/commentEdit");
        return mav;
    }
    /*
    コメントを編集するためにServiceクラスへ流す処理
     */
    @PutMapping("/updateComment/{id}/{content_id}")
    public ModelAndView updateComment (@PathVariable Integer id,
            @PathVariable Integer content_id, @ModelAttribute("commentModel") CommentForm comment) {
        comment.setId(id);
        //Serviceクラスへ流す
        commentService.saveComment(comment);
        return new ModelAndView("redirect:/");
    }

    /*
    コメントを削除するためにServiceクラスへ流す処理
     */
    @DeleteMapping("/deleteComment/{id}")
    public ModelAndView deleteContent(@PathVariable Integer id) {
        //削除処理を行うためServiceクラスへ流す
        commentService.deleteComment(id);
        //先頭画面(ルート)にリダイレクト
        return new ModelAndView("redirect:/");
    }
}
