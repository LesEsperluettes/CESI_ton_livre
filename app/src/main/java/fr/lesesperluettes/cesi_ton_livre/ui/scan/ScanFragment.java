package fr.lesesperluettes.cesi_ton_livre.ui.scan;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import fr.lesesperluettes.cesi_ton_livre.Book;
import fr.lesesperluettes.cesi_ton_livre.R;
import fr.lesesperluettes.cesi_ton_livre.UserActivity;
import fr.lesesperluettes.cesi_ton_livre.enums.BookCardType;
import fr.lesesperluettes.cesi_ton_livre.views.BookCardView;

public class ScanFragment extends Fragment {

    private ScanViewModel scanViewModel;

    private DecoratedBarcodeView barcodeView;
    private String lastText;

    private TextView txtResult;
    private BookCardView bookCard;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if(result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }

            lastText = result.getText();
            txtResult.setText(lastText);

            bookCard.loadBookFromApi(bookCard.getContext(),lastText);
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set fragment theme color
        UserActivity activity = (UserActivity) getActivity();
        int theme = getArguments().getInt("theme");
        activity.setTheme(theme);
        activity.setActivityColor();

        // Inflate ViewModel for the fragment
        scanViewModel = new ViewModelProvider(this).get(ScanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_scan, container, false);

        barcodeView = (DecoratedBarcodeView) root.findViewById(R.id.barcode_scanner);
        txtResult = (TextView) root.findViewById(R.id.barcode_txtResult);

        // Add book card
        final LinearLayout v = (LinearLayout) root.findViewById(R.id.barcode_cardLayout);
        BookCardView bookCardView = new BookCardView(getContext(),null, BookCardType.SCAN,new Book());
        bookCardView.setFragmentActivity(getActivity());
        this.bookCard = bookCardView;
        v.addView(bookCardView);

        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.CODE_39);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.initializeFromIntent(getActivity().getIntent());
        barcodeView.decodeContinuous(callback);

        // Set test text
        //final TextView textView = root.findViewById(R.id.text_scan);
        //scanViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {
        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }
}