package fr.lesesperluettes.cesi_ton_livre.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.core.content.res.ResourcesCompat;

import fr.lesesperluettes.cesi_ton_livre.R;
import fr.lesesperluettes.cesi_ton_livre.enums.BookCardStates;

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

        this.setState(context, BookCardStates.AVAILABLE);
    }

    public void setState(Context context, BookCardStates state){
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        @ColorInt int activatedColor = typedValue.data;

        int disabledColor = ResourcesCompat.getColor(getResources(),R.color.gray_3,null);

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
        }
    }

    private void updateBtn(String text, int color, int icon){
        btnStatus.setText(text);
        btnStatus.setBackgroundColor(color);
        btnStatus.setCompoundDrawablesWithIntrinsicBounds(0,0,icon,0);
    }
}
