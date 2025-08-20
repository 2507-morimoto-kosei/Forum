package com.example.forum.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

//バリデーションを実装するクラスはConstraintValidator<A, B>を実現しなければならない
//Aは関連付けるアノテーション名、Bは検査する対象の型
public class NoSpaceValidator implements ConstraintValidator<NoSpace, String> {
    @Override
    //isValidにロジックを書き込む。名前はisValid固定っぽい。String valueは検査対象を示す
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //値がnullの場合はエラーとする
        if (Objects.isNull(value)) return false;
        //strip()→文字列の前後の空白を取り除く。length()でその長さが0より大きければOK。0ならエラー(つまり空白)
        return value.strip().length() > 0;
    }
}
