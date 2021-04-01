package fr.lesesperluettes.cesi_ton_livre.api;

import java.util.Map;
import java.util.function.Consumer;

import fr.lesesperluettes.cesi_ton_livre.api.models.Book;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenLibraryApi {

    private final static String API_URL = "https://openlibrary.org/api/";
    private Retrofit retrofit;
    private OpenLibraryService service;

    public OpenLibraryApi(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(OpenLibraryService.class);
    }

    public void getBook(String ISBN, Consumer<Book> consumer) {
        String tagISBN = "ISBN:"+ISBN;
        // Create call to api
        Call<Map<String, Book>> books = this.service.getBooks(tagISBN,"json","data");
        books.enqueue(new Callback<Map<String, Book>>() {
            @Override
            public void onResponse(Call<Map<String, Book>> call, Response<Map<String, Book>> response) {
                Map<String, Book> res = response.body();
                Book book = res.get(tagISBN);
                consumer.accept(book);
            }

            @Override
            public void onFailure(Call<Map<String, Book>> call, Throwable t) {
                consumer.accept(null);
            }
        });
    }
}
