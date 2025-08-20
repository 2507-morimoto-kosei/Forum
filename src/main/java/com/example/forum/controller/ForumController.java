package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.controller.form.SearchForm;
import com.example.forum.service.CommentService;
import com.example.forum.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.model.IComment;

import java.util.List;

//Controllerであることを明示
@Controller
public class ForumController {
    //被依存先であると明示(ReportServiceインスタンスが自動注入される)
    @Autowired
    ReportService reportService;

    @Autowired
    CommentService CommentService;

    /*
    全投稿内容を表示する処理
     */
    //GETリクエストを受け取るメソッドであることを明示
    @GetMapping
    public ModelAndView top() {
        //viewに渡すデータ(Model)と渡す先(view)指定をまとめて管理するクラス
        ModelAndView mav = new ModelAndView();
        //投稿を全件取得
        List<ReportForm> contentData = reportService.findAllReport();
        //返信を全件取得
        List<CommentForm> commentData = CommentService.findAllComment();
        //コメント用の空箱用意
        CommentForm commentForm = new CommentForm();
        //投稿絞込用の空箱用意
        SearchForm searchForm = new SearchForm();
        //画面遷移先を指定
        mav.setViewName("/top");
        //ReportForm型のデータを扱う投稿データListをmavに登録
        mav.addObject("contents", contentData);
        //コメント用の空箱をmovに登録
        mav.addObject("commentModel", commentForm);
        //投稿絞込用の空箱をmovに登録
        mav.addObject("SearchModel", searchForm);
        //DBから取得した返信データListをmavに登録
        mav.addObject("comments", commentData);
        return mav;
    }

    /*
    絞込日時に応じて画面表示
     */
    @GetMapping("/search")
    public ModelAndView select(@ModelAttribute("SearchModel") SearchForm search) {
        //viewに渡すデータ(Model)と渡す先(view)指定をまとめて管理するクラス
        ModelAndView mav = new ModelAndView();
        //日付の範囲の投稿を取得
        List<ReportForm> searchData = reportService.findSearchReport(search);
        //全部の返信を取得(Viewで疑似的に条件分岐させてるよ)
        List<CommentForm> commentData = CommentService.findAllComment();
        //コメント用の空箱用意
        CommentForm commentForm = new CommentForm();
        //画面遷移先を指定
        mav.setViewName("/top");
        //ReportForm型のデータを扱う投稿データListをmavに登録
        mav.addObject("contents", searchData);
        //コメント用の空箱をmovに登録
        mav.addObject("commentModel", commentForm);
        //DBから取得した返信データListをmavに登録
        mav.addObject("comments", commentData);
        return mav;
    }

    /*
    新規投稿入力画面を表示する処理
     */
    //Getリクエストに対応するメソッドであることを明示
    @GetMapping("/new")
    public ModelAndView newContent() {
        //viewに渡すデータと渡す先のviewの指定をまとめて行うオブジェクト生成
        ModelAndView mav = new ModelAndView();
        //空のFormクラスを準備(新規投稿時、ユーザはこれに値を詰めてくる)
        ReportForm reportForm = new ReportForm();
        //画面遷移先の指定(new.htmlへ)
        mav.setViewName("/new");
        //空のForm箱をviewに渡すために保持
        mav.addObject("formModel", reportForm);
        return mav;
    }
    /*
    新規投稿をDBに登録するためServiceクラスへ流す処理
     */
    //POSTリクエストに対応したメソッドであることを明示
    @PostMapping("/add")
    //BindingResultクラスはバリデーションの結果を保持するクラス。ValidationのModelAndView版みたいな
    //BindingResultクラスは@Validatedとバウンドクラスの直後に記述する
    public ModelAndView addContent(@ModelAttribute("formModel") @Validated ReportForm reportForm, BindingResult result) {
        //入力不備があるか確認
        if(result.hasErrors()) {
            //もしあれば新規投稿作成画面に突き返す
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/new");
            return mav;
        }
        //投稿をテーブルへ格納するためServiceクラスへ流す
        reportService.saveReport(reportForm);
        //先頭画面(ルート)にリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
    投稿削除するための処理
     */
    //DELETEリクエストに対応したメソッドであることを明示
    @DeleteMapping("/delete/{id}")
    //top.htmlから渡された値を@ModelAttributeで取り出し、reportFormへ詰めている
    public ModelAndView deleteContent(@PathVariable Integer id) {
        //削除処理を行うためServiceクラスへ流す
        reportService.deleteReport(id);
        //先頭画面(ルート)にリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
    投稿編集画面を表示するための処理
     */
    //GETリクエストを受付、idを動的に受取る
    @GetMapping("/edit/{id}")
    public ModelAndView editContent(@PathVariable Integer id) {
        //ModelAndViewはviewに渡すデータと渡し先を管理するオブジェクト
        ModelAndView mav = new ModelAndView();
        //受取ったidを元に編集する投稿をDBから取得
        ReportForm report = reportService.editReport(id);
        //DBから取得した編集対象の投稿をセット
        mav.addObject("formModel", report);
        //画面遷移先を指定
        mav.setViewName("/edit");
        return mav;
    }
    /*
    投稿を編集するためにServiceクラスへ流す処理
     */
    @PutMapping("/update/{id}")
    public ModelAndView updateContent (@PathVariable Integer id,
                            @ModelAttribute("formModel") @Validated ReportForm report, BindingResult result) {
        report.setId(id);
        //入力不備があるか確認
        if(result.hasErrors()) {
            //もしあれば新規投稿作成画面に突き返す
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/edit");
            return mav;
        }
        //Serviceクラスへ流す
        reportService.saveReport(report);
        return new ModelAndView("redirect:/");
    }
}