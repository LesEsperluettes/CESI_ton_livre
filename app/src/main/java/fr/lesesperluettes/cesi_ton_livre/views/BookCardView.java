package fr.lesesperluettes.cesi_ton_livre.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.jar.Attributes;

import fr.lesesperluettes.cesi_ton_livre.R;

public class BookCardView extends LinearLayout {

    private LinearLayout rootLayout;

    private ImageView imgBook;
    private TextView txtTitle;
    private TextView txtAuthors;
    private TextView txtPublishers;
    private TextView txtDate;
    private TextView txtISBN;
    private Button btnStatus;

    public BookCardView(Context context, AttributeSet att) {
        super(context,att);
        initControl(context);
    }

    private void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.control_book,this);

        rootLayout = (LinearLayout) findViewById(R.id.bookcard_root);
        imgBook = (ImageView) findViewById(R.id.bookcard_imgBook);
        txtTitle = (TextView) findViewById(R.id.bookcard_txtTitle);
        txtAuthors = (TextView) findViewById(R.id.bookcard_txtAuthors);
        txtPublishers = (TextView) findViewById(R.id.bookcard_txtPublishers);
        txtDate = (TextView) findViewById(R.id.bookcard_txtDate);
        txtISBN = (TextView) findViewById(R.id.bookcard_txtISBN);
        btnStatus = (Button) findViewById(R.id.bookcard_btnStatus);

        rootLayout.setClipToOutline(true);
        imgBook.setImageResource(R.mipmap.ic_test_book_foreground);
    }
}
