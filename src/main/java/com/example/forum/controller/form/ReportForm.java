package com.example.forum.controller.form;

import lombok.Getter;
import lombok.Setter;

//Viewへの入出力時に使用する入れ物クラス
//Lombokでゲッター/セッターをアノテーションで処理
@Getter
@Setter
public class ReportForm {
    private int id;
    private String content;
}
