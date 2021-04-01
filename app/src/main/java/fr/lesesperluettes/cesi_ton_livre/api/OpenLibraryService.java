package fr.lesesperluettes.cesi_ton_livre.api;

import fr.lesesperluettes.cesi_ton_livre.api.models.Book;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;

public interface OpenLibraryService {
    @GET("isbn/books?bibkeys=ISBN:{ISBN}&format=json&jscmd=data")
    Call<Book> getBook(@Part("ISBN") String ISBN);
}
