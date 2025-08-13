package com.example.forum.controller;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

//Controllerであることを明示
@Controller
public class ForumController {
    //被依存先であると明示(ReportServiceインスタンスが自動注入される)
    @Autowired
    ReportService reportService;

    /*
    全投稿内容表示処理
     */
    //GETリクエストを受け取るメソッドであることを明示
    @GetMapping
    public ModelAndView top() {
        //viewに渡すデータ(Model)と渡す先(view)指定をまとめて管理するクラス
        ModelAndView mav = new ModelAndView();
        //投稿を全件取得
        List<ReportForm> contentData = reportService.findAllReport();
        //画面遷移先を指定
        mav.setViewName("/top");
        //ReportForm型のデータを扱う投稿データListをmavに登録
        mav.addObject("contents", contentData);
        return mav;
    }
    /*
    新規投稿画面表示
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
    新規投稿をDBに登録するために流す処理
     */
    //POSTリクエストに対応したメソッドであることを明示
    @PostMapping("/add")
    //new.htmlから渡された値を@ModelAttributeで取り出し、reportFormへ詰めている
    public ModelAndView addContent(@ModelAttribute("formModel") ReportForm reportForm) {
        //投稿をテーブルへ格納するためServiceクラスへ流す
        reportService.saveReport(reportForm);
        //先頭画面(ルート)にリダイレクト
        return new ModelAndView("redirect:/");
    }
    /*
    投稿削除するための処理
     */
    //POSTリクエストに対応したメソッドであることを明示
    @DeleteMapping("/delete")
    //top.htmlから渡された値を@ModelAttributeで取り出し、reportFormへ詰めている
    public ModelAndView deleteContent(@ModelAttribute("contents") ReportForm reportForm) {
        //投稿をテーブルへ格納するためServiceクラスへ流す
        reportService.deleteReport(reportForm);
        //先頭画面(ルート)にリダイレクト
        return new ModelAndView("redirect:/");
    }
}

