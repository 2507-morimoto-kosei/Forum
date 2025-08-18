package com.example.forum.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class SearchForm {
    private LocalDate startDate;
    private LocalDate endDate;
}
