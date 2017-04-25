package com.phuongdong.bookstore.model;

import java.io.Serializable;

/**
 * Created by phuongdong on 4/13/17.
 */

public class Book implements Serializable{
    private String idBook;
    private String codeBook;
    private String nameBook;
    private String datePublished;
    private String authorId;

    public Book(){}

    public Book(String idBook, String codeBook, String nameBook, String datePublished, String authorId) {
        this.idBook = idBook;
        this.codeBook = codeBook;
        this.nameBook = nameBook;
        this.datePublished = datePublished;
        this.authorId = authorId;
    }

    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    public String getCodeBook() {
        return codeBook;
    }

    public void setCodeBook(String codeBook) {
        this.codeBook = codeBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "[" + idBook + "]" + codeBook + "--" + nameBook + "(Published: " + datePublished + ")";
    }
}
