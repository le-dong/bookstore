package com.phuongdong.bookstore.model;

import java.io.Serializable;

/**
 * Created by phuongdong on 4/13/17.
 */

public class Author implements Serializable{
    private String idAuthor;
    private String codeAuthor;
    private String nameAuthor;

    public Author(){}

    public Author(String idAuthor, String codeAuthor, String nameAuthor) {
        this.idAuthor = idAuthor;
        this.codeAuthor = codeAuthor;
        this.nameAuthor = nameAuthor;
    }

    public String getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getCodeAuthor() {
        return codeAuthor;
    }

    public void setCodeAuthor(String codeAuthor) {
        this.codeAuthor = codeAuthor;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    @Override
    public String toString() {
        return  "[" + idAuthor + "]" + codeAuthor + "--" + nameAuthor;
    }
}
