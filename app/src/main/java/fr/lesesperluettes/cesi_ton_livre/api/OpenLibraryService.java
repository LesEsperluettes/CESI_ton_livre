package fr.lesesperluettes.cesi_ton_livre.api;

import java.util.Map;

import fr.lesesperluettes.cesi_ton_livre.api.models.Book;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenLibraryService {
    @GET("books")
    Call<Map<String, Book>> getBooks(@Query(value = "bibkeys",encoded = true) String bibkeys, @Query("format") String format, @Query("jscmd") String jscmd);
}
