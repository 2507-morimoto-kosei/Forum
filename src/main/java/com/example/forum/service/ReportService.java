package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //DBとのやり取りは～entityクラスでやりたいのでReportを指定(Reportは@entityしてたね！)
        //findAllはjpaRepositoryの便利メソッド(SELECT処理)
        List<Report> results = reportRepository.findAllByOrderByIdDesc();
        //DBから来た情報をFormに詰め替えて保持
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }
    /*
    Entity→Formに詰め替え処理
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
            //Listに追加
            reports.add(report);
        }
        return reports;
    }
    /*
    投稿内容をDBに追加処理
     */
    public void saveReport(ReportForm reqReport) {
        //Form→Entityに詰め替えられたを変数に保持
        Report saveReport = setReportEntity(reqReport);
        //saveはjpaRepositoryの便利メソッド(INSERT処理)
        reportRepository.save(saveReport);
    }
    /*
    投稿内容をDBから削除処理
     */
    public void deleteReport(ReportForm reqReport) {
        //Form→Entityに詰め替えられたを変数に保持
        Report deletReport = setReportEntity(reqReport);
        //saveはjpaRepositoryの便利メソッド(INSERT処理)
        reportRepository.deleteById(deletReport.getId());
    }
    /*
    Form→Entityに詰替え処理
     */
    //Reportは@Entityされてたね
    private Report setReportEntity(ReportForm reqReport) {
        //詰替え先のEntityオブジェクトを生成
        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());
        return report;
    }
}
