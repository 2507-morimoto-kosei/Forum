package com.example.forum.repository.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

//DBアクセス時に使用する入れ物クラス。Entityクラスは基本DBのテーブルと1:1対応する
//入れ物の役割であるEntityを明示
@Entity
//Entityクラスと対応するDBのテーブル名を指定
@Table(name = "report")
//Lombokでゲッター/セッターをアノテーションで処理
@Getter
@Setter
public class Report {
    //DBテーブルの主キーであることを明示
    @Id
    //DBテーブルのカラムに対応(プロパティそれぞれにつける)
    @Column
    //採番はDBに任せる宣言(今回はidカラムのSERIALの増分に従う)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //DBテーブルのカラムに対応(プロパティそれぞれにつける)
    @Column
    private String content;

    @Column
    private Date created_date;

    @Column
    private Date updated_date;
}
