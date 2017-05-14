package com.zhukaihao.zhihudailypurify.bean;

import java.util.List;

public final class DailyNews {
    private String date;                // 日期
    private String dailyTitle;          // 小标题
    private String thumbnailUrl;        // 缩略图
    private List<Question> questions;   // 问题信息：问题，链接

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDailyTitle() {
        return dailyTitle;
    }

    public void setDailyTitle(String dailyTitle) {
        this.dailyTitle = dailyTitle;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public boolean hasMultipleQuestions() {
        return this.getQuestions().size() > 1;
    }
}