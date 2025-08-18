package com.example.forum.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

//Viewへの入出力時に使用する入れ物クラス
//Lombokでゲッター/セッターをアノテーションで処理
@Getter
@Setter
public class ReportForm {
    private int id;
    private String content;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;
}
