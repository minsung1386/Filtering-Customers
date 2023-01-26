package edu.skku.cs.final_project;

public class PostModel {
    private String PostId;
    private String Date;
    private String Author;
    private String ReportedUsername;
    private String ReportedPhoneNum;
    private int Views;
    private String Title;
    private String Content;
    private String ImgSrc;
    private int Good;
    private int Bad;

    public int getGood() {
        return Good;
    }

    public void setGood(int good) {
        Good = good;
    }

    public int getBad() {
        return Bad;
    }

    public void setBad(int bad) {
        Bad = bad;
    }

    public PostModel(){}
    public PostModel(String author){
        this.Author=author;
        this.Good=0;
        this.Bad=0;
        this.Views=0;
    }
    public PostModel(String postId, String date, String author, String reportedUsername, String reportedPhoneNum, int views, String title, String content, String imgSrc) {
        PostId = postId;
        Date = date;
        Author = author;
        ReportedUsername = reportedUsername;
        ReportedPhoneNum = reportedPhoneNum;
        Views = views;
        Title = title;
        Content = content;
        ImgSrc = imgSrc;
        Good=0;
        Bad=0;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getReportedUsername() {
        return ReportedUsername;
    }

    public void setReportedUsername(String reportedUsername) {
        ReportedUsername = reportedUsername;
    }

    public String getReportedPhoneNum() {
        return ReportedPhoneNum;
    }

    public void setReportedPhoneNum(String reportedPhoneNum) {
        ReportedPhoneNum = reportedPhoneNum;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImgSrc() {
        return ImgSrc;
    }

    public void setImgSrc(String imgSrc) {
        ImgSrc = imgSrc;
    }
}
