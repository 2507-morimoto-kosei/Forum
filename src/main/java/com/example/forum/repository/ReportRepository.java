package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Repositoryであることを明示
@Repository
//JpaRepositorはDB操作を簡単にしてくれるインターフェース
//引数にDBテーブルと対応しているEntityクラスを指定する
//ここのRepositoryはインターフェースなことに注意
public interface ReportRepository extends JpaRepository<Report, Integer> {
    public List<Report> findAllByOrderByIdDesc();
}
