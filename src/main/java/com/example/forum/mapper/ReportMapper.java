package com.example.forum.mapper;

import com.example.forum.repository.entity.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
    List<Report> findAllOrderByupdated_dateDesc();
}
