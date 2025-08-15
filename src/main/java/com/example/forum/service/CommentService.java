package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.entity.Comment;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    /*
    投稿内容をDBに追加処理
     */
    public void saveComment(CommentForm reqComment) {
        //Form→Entityに詰め替えられたを変数に保持
        Comment saveComment = setCommentEntity(reqComment);
        //saveはjpaRepositoryの便利メソッド(INSERTまたはUPDATE処理)
        commentRepository.save(saveComment);
    }
    /*
    Form→Entityに詰替え処理(追加処理用)
     */
    //Reportは@Entityされてたね
    private Comment setCommentEntity(CommentForm reqComment) {
        //詰替え先のEntityオブジェクトを生成
        Comment comment = new Comment();
        comment.setId(reqComment.getId());
        comment.setContent_id(reqComment.getContent_id());
        comment.setComment(reqComment.getComment());
        return comment;
    }

    /*
    コメントを削除する処理
     */
    public void deleteComment(Integer id) {
        //Form→Entityに詰め替えメソッドを呼び、戻り値を変数に保持
        Comment deleteComment = setCommentEntity(id);
        //deleteByIdはjpaRepositoryの便利メソッド(DELETE処理)
        commentRepository.deleteById(deleteComment.getId());
    }
    /*
    Form→Entityに詰替え処理(削除処理用)
    */
    private Comment setCommentEntity(Integer id) {
        //詰替え先のEntityオブジェクトを生成し削除対象のIdを詰める
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }


    /*
    返信レコード全件取得処理
     */
    //controllerやviewで使うデータなので～FormクラスとしてListで管理
    public List<CommentForm> findAllComment() {
        //findAllはjpaRepositoryの便利メソッド(SELECT処理)
        //reportRepository.findAllByOrderByIdDesc();はもし空だったらEntity型の空のリストで返ってくる
        List<Comment> results = commentRepository.findAllByOrderByIdDesc();
        //DBから来た情報をFormに詰め替えて保持
        List<CommentForm> comments = setCommentForm(results);
        return comments;
    }
    /*
    レコードを1件取得
     */
    public CommentForm editComment(Integer id) {
        List<Comment> results = new ArrayList<>();
        //単一検索なのでOptional型で返ってくる。だからEntity型にキャストする
        results.add((Comment) commentRepository.findById(id).orElse(null));
        //controllerに渡すためにsetCommentFormメソッドを使ってFromクラスに詰替え
        List<CommentForm> comments = setCommentForm(results);
        //idに合致したやつを拾ってくるから当然1個=インデックス番号0となる
        return comments.get(0);
    }
    /*
    Entity→Formに詰め替え処理(レコード取得用)
     */
    private List<CommentForm> setCommentForm(List<Comment> results) {
        //詰替え作業はList型みたいなインターフェースで扱うと保守が楽だよ
        List<CommentForm> comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            //詰替え先のオブジェクト生成
            CommentForm comment = new CommentForm();
            //引数で渡されたEntity箱からi番目の値を取得
            Comment result = results.get(i);
            //Listで管理するForm箱に値をセット
            comment.setId(result.getId());
            comment.setContent_id(result.getContent_id());
            comment.setComment(result.getComment());
            //Listに追加
            comments.add(comment);
        }
        return comments;
    }
}
