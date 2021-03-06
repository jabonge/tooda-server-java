package com.ddd.tooda.domain.diary.dto;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class MonthlyDiaryMetaRowMapper implements RowMapper<MonthlyDiaryMetaRow> {

    @Override
    public MonthlyDiaryMetaRow mapRow(ResultSet rs, int rowNum) throws SQLException {
        MonthlyDiaryMetaRow row = new MonthlyDiaryMetaRow();
        row.setId(rs.getLong("id"));
        row.setYear(rs.getInt("year"));
        row.setMonth(rs.getInt("month"));
        row.setTotalCount(rs.getInt("total_count"));
        row.setSticker(rs.getString("sticker"));
        row.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        row.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return row;
    }
}
