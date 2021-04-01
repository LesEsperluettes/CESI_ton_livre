package fr.lesesperluettes.cesi_ton_livre.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.core.content.res.ResourcesCompat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import fr.lesesperluettes.cesi_ton_livre.R;
import fr.lesesperluettes.cesi_ton_livre.api.OpenLibraryApi;
import fr.lesesperluettes.cesi_ton_livre.api.models.Author;
import fr.lesesperluettes.cesi_ton_livre.api.models.Book;
import fr.lesesperluettes.cesi_ton_livre.api.models.Publisher;
import fr.lesesperluettes.cesi_ton_livre.enums.BookCardStates;

public class BookCardView extends LinearLayout {

    private LinearLayout rootLayout;
    private LinearLayout linearLayout;

    private ImageView imgBook;
    private TextView txtTitle;
    private TextView txtAuthors;
    private TextView txtPublishers;
    private TextView txtDate;
    private TextView txtISBN;
    private Button btnStatus;
    private ProgressBar progLoading;

    public BookCardView(Context context, AttributeSet att) {
        super(context,att);
        initControl(context);
    }

    private void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_book,this);

        rootLayout = (LinearLayout) findViewById(R.id.bookcard_root);
        linearLayout = (LinearLayout) findViewById(R.id.bookcard_layout);

        imgBook = (ImageView) findViewById(R.id.bookcard_imgBook);
        txtTitle = (TextView) findViewById(R.id.bookcard_txtTitle);
        txtAuthors = (TextView) findViewById(R.id.bookcard_txtAuthors);
        txtPublishers = (TextView) findViewById(R.id.bookcard_txtPublishers);
        txtDate = (TextView) findViewById(R.id.bookcard_txtDate);
        txtISBN = (TextView) findViewById(R.id.bookcard_txtISBN);
        btnStatus = (Button) findViewById(R.id.bookcard_btnStatus);
        progLoading = (ProgressBar) findViewById(R.id.bookcard_progLoading);

        rootLayout.setClipToOutline(true);

        // Example de chargement asynchrone avec OpenLibrary
        // TODO implémenter la liaison avec la base
        //loadBookFromApi(context,"9782871292067");

    }

    public void setState(Context context, BookCardStates state){
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        @ColorInt int activatedColor = typedValue.data;

        int disabledColor = ResourcesCompat.getColor(getResources(),R.color.gray_3,null);

        setIsLoading(false);

        switch(state){
            case AVAILABLE:
                updateBtn("Disponible",activatedColor,R.drawable.ic_baseline_check_circle_24);
                break;
            case NOT_AVAILABLE:
                updateBtn("Indisponible",disabledColor,R.drawable.ic_baseline_cancel_24);
                break;
            case BORROW:
                updateBtn("Emprunter",activatedColor,R.drawable.ic_book_reader_solid);
                break;
            case LATE:
                updateBtn("En retard !",disabledColor,R.drawable.ic_baseline_cancel_24);
                break;
            case NOT_LATE:
                updateBtn("temps restant",activatedColor,R.drawable.ic_baseline_check_circle_24);
                break;
            case LOADING:
                setIsLoading(true);
                break;
        }
    }

    private void updateBtn(String text, int color, int icon){
        btnStatus.setText(text);
        btnStatus.setBackgroundColor(color);
        btnStatus.setCompoundDrawablesWithIntrinsicBounds(0,0,icon,0);
    }

    public void loadBook(Book book) throws IOException {
        if(book == null) return;

        String authors = book.getAuthors().stream()
                            .map(Author::getName)
                            .collect(Collectors.joining(", "));

        String publishers = book.getPublishers().stream()
                .map(Publisher::getName)
                .collect(Collectors.joining(", "));

        this.txtTitle.setText(book.getTitle());
        this.txtAuthors.setText(authors);
        this.txtPublishers.setText(publishers);
        this.txtDate.setText(book.getPublishDate());
        this.txtISBN.setText("ISBN : "+book.getIdentifiers().getIsbn13().get(0));

        // Image loading from url
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // Query the image from the web
            Bitmap bmp = null;
            try {
                URL thumbUrl = new URL(book.getCover().getMedium());
                bmp = BitmapFactory.decodeStream(thumbUrl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap finalBmp = bmp;
            handler.post(() -> {
                // Apply the image to the ImageView
                this.imgBook.setImageBitmap(finalBmp);
            });
        });
    }

    public void loadBookFromApi(Context context, String ISBN) {
        OpenLibraryApi api = new OpenLibraryApi();
        this.setState(context, BookCardStates.LOADING);
        api.getBook(ISBN, book -> {
            try {
                this.loadBook(book);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.setState(context, BookCardStates.AVAILABLE);
        });
    }

    private void setIsLoading(boolean loading){
        if(loading){
            imgBook.setVisibility(GONE);
            linearLayout.setVisibility(GONE);
            progLoading.setVisibility(VISIBLE);
        }else{
            imgBook.setVisibility(VISIBLE);
            linearLayout.setVisibility(VISIBLE);
            progLoading.setVisibility(GONE);
        }
    }
}
