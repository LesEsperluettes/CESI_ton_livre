
package fr.lesesperluettes.cesi_ton_livre.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Identifiers {

    @SerializedName("goodreads")
    @Expose
    private List<String> goodreads = null;
    @SerializedName("librarything")
    @Expose
    private List<String> librarything = null;
    @SerializedName("isbn_10")
    @Expose
    private List<String> isbn10 = null;
    @SerializedName("isbn_13")
    @Expose
    private List<String> isbn13 = null;
    @SerializedName("openlibrary")
    @Expose
    private List<String> openlibrary = null;

    public List<String> getGoodreads() {
        return goodreads;
    }

    public void setGoodreads(List<String> goodreads) {
        this.goodreads = goodreads;
    }

    public List<String> getLibrarything() {
        return librarything;
    }

    public void setLibrarything(List<String> librarything) {
        this.librarything = librarything;
    }

    public List<String> getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(List<String> isbn10) {
        this.isbn10 = isbn10;
    }

    public List<String> getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(List<String> isbn13) {
        this.isbn13 = isbn13;
    }

    public List<String> getOpenlibrary() {
        return openlibrary;
    }

    public void setOpenlibrary(List<String> openlibrary) {
        this.openlibrary = openlibrary;
    }

}
