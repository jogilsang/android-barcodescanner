package com.practice.bacodescanner.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.practice.bacodescanner.R;
import com.practice.bacodescanner.handler.BackPressCloseHandler;

/**
 * Created by samsung on 2017-08-28.
 */

public class ScannerProcessActivity extends BaseActivity implements DecoratedBarcodeView.TorchListener {

    public static final int RESULT_NEXT = 2;
    private static final String TAG = "ScannerProcessActivity" ;

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private BackPressCloseHandler backPressCloseHandler;

    /**
     * button
     */
    private ImageButton switchFlashlightButton;
    private ImageButton switchSortButton;

    private Boolean switchFlashlightButtonCheck;

    private TextView valueView;
    private TextView typeView;

    private Button btnCancel;

    private BarcodeView barcodeView;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scanner);

        initView();

        initSet();

        setListener();

        if (!hasFlash()) {
            switchFlashlightButton.setVisibility(View.GONE);
        }

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();

    }

    private void setListener() {

        valueView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                String value = valueView.getText().toString();

                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("label", value);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplication(), getString(R.string.msg_copy),Toast.LENGTH_LONG).show();

                return false;
            }
        });

        barcodeScannerView.setTorchListener(this);

        switchSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items2 = {"QR_CODE",
                        "DATA_MATRIX",
                        "UPC_A",
                        "UPC_E",
                        "EAN_8",
                        "EAN_13",
                        "CODE_39",
                        "CODE_93",
                        "CODE_128",
                        "ITF",
                        "RSS_14",
                        "RSS_EXPANDED"};

                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(ScannerProcessActivity.this);

                // 제목셋팅
                alertDialogBuilder2.setTitle(getString(R.string.msg_dialog));
                alertDialogBuilder2.setSingleChoiceItems(items2, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                // 선택한거 출력
                                Log.d(TAG, items2[id].toString());
                                String selectedText = items2[id].toString();

                                // 프로그램을 종료한다
                                Toast.makeText(getApplicationContext(),
                                        items2[id] + getString(R.string.msg_selected),
                                        Toast.LENGTH_SHORT).show();

                                // 바코드 타입도 바꿔주기기
                               typeView.setText(selectedText);
                               valueView.setText(getString(R.string.text_value));

                               // id 값
                               Log.d(TAG, String.valueOf(id));
                               choice = id;

                               dialog.dismiss();

                                // 액티비티 종료
                                //Intent returnIntent = new Intent();
                                //setResult(Activity.RESULT_FIRST_USER, returnIntent);
                                //finish();
                            }
                        });

                // 다이얼로그 생성
                AlertDialog alertDialog2 = alertDialogBuilder2.create();

                // 다이얼로그 보여주기
                alertDialog2.show();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 액티비티 종료
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();

            }
        });

    }

    public void initView() {

        // 배너광고
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // 바코드 전체 영역
        barcodeScannerView = (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);

        // 바코드 감지하는 영역
        barcodeView= (BarcodeView)findViewById(R.id.zxing_barcode_surface);

        // 완료,취소 버튼
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        switchFlashlightButton = (ImageButton) findViewById(R.id.switch_flashlight);
        switchSortButton = (ImageButton) findViewById(R.id.switch_sort);
        
        // 내용
        valueView = (TextView) findViewById(R.id.zxing_count_view);
        typeView = (TextView) findViewById(R.id.zxing_type_view);

    }

    public void initSet() {

        switchFlashlightButtonCheck = true;
        backPressCloseHandler = new BackPressCloseHandler(this);

        // 카운트 뷰 숫자 설정
        valueView.setText(value);
        typeView.setText(type);

    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    public void switchFlashlight(View view) {
        if (switchFlashlightButtonCheck) {
            barcodeScannerView.setTorchOn();
        } else {
            barcodeScannerView.setTorchOff();
        }
    }

    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onTorchOn() {
        switchFlashlightButton.setImageResource(R.drawable.ic_flash_on_white_36dp);
        switchFlashlightButtonCheck = false;
    }

    @Override
    public void onTorchOff() {
        switchFlashlightButton.setImageResource(R.drawable.ic_flash_off_white_36dp);
        switchFlashlightButtonCheck = true;
    }
}