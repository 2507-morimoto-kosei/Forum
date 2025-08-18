package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.controller.form.SearchForm;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//Serviceクラスであることを明示
@Service
public class ReportService {
    //被依存先であることを明示(ReportRepositoryインスタンスが自動注入される)
    @Autowired
    ReportRepository reportRepository;

    /*
    レコード全件取得処理
     */
    //controllerやviewで使うデータなので～FormクラスとしてListで管理
    public List<ReportForm> findAllReport() {
        //findAllはjpaRepositoryの便利メソッド(SELECT処理)
        //reportRepository.findAllByOrderByIdDesc();はもし空だったらEntity型の空のリストで返ってくる
        List<Report> results = reportRepository.findAllByOrderByIdDesc();
        //DBから来た情報をFormに詰め替えて保持
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }
    /*
    レコードを1件取得
     */
    public ReportForm editReport(Integer id) {
        List<Report> results = new ArrayList<>();
        //単一検索なのでOptional型で返ってくる。だからEntity型にキャストする
        results.add((Report) reportRepository.findById(id).orElse(null));
        //controllerに渡すためにsetReportFormメソッドを使ってFromクラスに詰替え
        List<ReportForm> reports = setReportForm(results);
        //idに合致したやつを拾ってくるから当然1個=インデックス番号0となる
        return reports.get(0);
    }
    /*
    絞込日に合わせて取得
     */
    public List<ReportForm> findSearchReport(SearchForm search) {
        //DBがTIMESTAMP型で日時情報を扱うので、対応できるように変換
        LocalDateTime startDate = search.getStartDate().atStartOfDay();
        LocalDateTime endDate = search.getEndDate().atTime(LocalTime.MAX);
        //開始日と終了日に合わせた投稿をDBから取得
        List<Report> results = reportRepository.findBycreatedDateBetween(startDate, endDate);
        //controllerに渡すためにsetReportFormメソッドを使ってFromクラスに詰替え
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }
    /*
    Entity→Formに詰め替え処理(レコード取得用)
     */
    private List<ReportForm> setReportForm(List<Report> results) {
        //詰替え作業はList型みたいなインターフェースで扱うと保守が楽だよ
        List<ReportForm> reports = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            //詰替え先のオブジェクト生成
            ReportForm report = new ReportForm();
            //引数で渡されたEntity箱からi番目の値を取得
            Report result = results.get(i);
            //Listで管理するForm箱に値をセット
            report.setId(result.getId());
            report.setContent(result.getContent());
            report.setCreated_date(result.getCreatedDate());
            report.setUpdated_date(result.getUpdatedDate());
            //Listに追加
            reports.add(report);
        }
        return reports;
    }

    /*
    投稿内容をDBに追加処理
     */
    public void saveReport(ReportForm reqReport) {
        //日時情報の付与
        reqReport.setCreated_date(LocalDateTime.now());
        reqReport.setUpdated_date(LocalDateTime.now());
        //Form→Entityに詰め替えられたを変数に保持
        Report saveReport = setReportEntity(reqReport);
        //saveはjpaRepositoryの便利メソッド(INSERT処理)
        reportRepository.save(saveReport);
    }
    /*
    Form→Entityに詰替え処理(追加処理用)
     */
    //Reportは@Entityされてたね
    private Report setReportEntity(ReportForm reqReport) {
        //詰替え先のEntityオブジェクトを生成
        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());
        report.setCreatedDate(reqReport.getCreated_date());
        report.setUpdatedDate(reqReport.getUpdated_date());
        return report;
    }

    /*
    投稿内容をDBから削除処理
     */
    public void deleteReport(Integer id) {
        //Form→Entityに詰め替えられた値を変数に保持
        Report deletReport = setReportEntity(id);
        //saveはjpaRepositoryの便利メソッド(INSERT処理)
        reportRepository.deleteById(deletReport.getId());
    }
    /*
    Form→Entityに詰替え処理(削除処理用)
     */
    //Reportは@Entityされてたね
    private Report setReportEntity(Integer id) {
        //詰替え先のEntityオブジェクトを生成
        Report report = new Report();
        report.setId(id);
        return report;
    }
}