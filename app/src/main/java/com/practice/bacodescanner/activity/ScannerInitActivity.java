package com.practice.bacodescanner.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.practice.bacodescanner.R;
import com.practice.bacodescanner.util.AdsFull;

import java.util.Collection;
import java.util.Collections;


public class ScannerInitActivity extends BaseActivity {

    public static final Collection<String> QR_CODE_TYPE = Collections.singleton("QR_CODE");
    public static final Collection<String> DATA_MATRIX_TYPE = Collections.singleton("DATA_MATRIX");
    public static final Collection<String> UPC_A_TYPE = Collections.singleton("UPC_A");
    public static final Collection<String> UPC_E_TYPE = Collections.singleton("UPC_E");
    public static final Collection<String> EAN_8_TYPE = Collections.singleton("EAN_8");

    public static final Collection<String> EAN_13_TYPE = Collections.singleton("EAN_13");
    public static final Collection<String> CODE_39_TYPE = Collections.singleton("CODE_39");
    public static final Collection<String> CODE_93_TYPE = Collections.singleton("CODE_93");
    public static final Collection<String> CODE_128_TYPE = Collections.singleton("CODE_128");
    public static final Collection<String> ITF_TYPE = Collections.singleton("ITF");

    public static final Collection<String> RSS_14_TYPE = Collections.singleton("RSS_14");
    public static final Collection<String> RSS_EXPANDED_TYPE = Collections.singleton("RSS_EXPANDED");

    public static final int QR_CODE_NUM = 0;
    public static final int DATA_MATRIX_NUM = 1;
    public static final int UPC_A_NUM = 2;
    public static final int UPC_E_NUM = 3;
    public static final int EAN_8_NUM = 4;

    public static final int EAN_13_NUM = 5;
    public static final int CODE_39_NUM = 6;
    public static final int CODE_93_NUM = 7;
    public static final int CODE_128_NUM = 8;
    public static final int ITF_NUM = 9;

    public static final int RSS_14_NUM = 10;
    public static final int RSS_EXPANDED_NUM = 11;
    private static final String TAG = "ScannerInitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // AdsFull adsFull = AdsFull.getInstance(this);

    }

    protected void onResume(){
        super.onResume();

        Log.d(TAG, "onResume");
        IntentIntegrator integrator = new IntentIntegrator(this);

        // 액티비티 실행
        integrator.setCaptureActivity(ScannerProcessActivity.class);

        switch (choice) {

            case QR_CODE_NUM :
                integrator.setDesiredBarcodeFormats(QR_CODE_TYPE);
                integrator.initiateScan(QR_CODE_TYPE);

                break;
            case DATA_MATRIX_NUM :
                integrator.setDesiredBarcodeFormats(DATA_MATRIX_TYPE);
                integrator.initiateScan(DATA_MATRIX_TYPE);
                break;
            case UPC_A_NUM :
                integrator.setDesiredBarcodeFormats(UPC_A_TYPE);
                integrator.initiateScan(UPC_A_TYPE);
                break;
            case UPC_E_NUM :
                integrator.setDesiredBarcodeFormats(UPC_E_TYPE);
                integrator.initiateScan(UPC_E_TYPE);
                break;
            case EAN_8_NUM :
                integrator.setDesiredBarcodeFormats(EAN_8_TYPE);
                integrator.initiateScan(EAN_8_TYPE);
                break;
            case EAN_13_NUM :
                integrator.setDesiredBarcodeFormats(EAN_13_TYPE);
                integrator.initiateScan(EAN_13_TYPE);
                break;
            case CODE_39_NUM :
                integrator.setDesiredBarcodeFormats(CODE_39_TYPE);
                integrator.initiateScan(CODE_39_TYPE);
                break;
            case CODE_93_NUM :
                integrator.setDesiredBarcodeFormats(CODE_93_TYPE);
                integrator.initiateScan(CODE_93_TYPE);
                break;
            case CODE_128_NUM :
                integrator.setDesiredBarcodeFormats(CODE_128_TYPE);
                integrator.initiateScan(CODE_128_TYPE);
                break;
            case ITF_NUM :
                integrator.setDesiredBarcodeFormats(ITF_TYPE);
                integrator.initiateScan(ITF_TYPE);
                break;
            case RSS_14_NUM :
                integrator.setDesiredBarcodeFormats(RSS_14_TYPE);
                integrator.initiateScan(RSS_14_TYPE);
                break;
            case RSS_EXPANDED_NUM :
                integrator.setDesiredBarcodeFormats(RSS_EXPANDED_TYPE);
                integrator.initiateScan(RSS_EXPANDED_TYPE);
                break;
            
        }
        
        // 바코드 타입 고정 : QR type 변경
        // integrator.setDesiredBarcodeFormats(integrator.QR_CODE_TYPES);
        // integrator.initiateScan(integrator.QR_CODE_TYPES);

    }

    // 숫자만 건지는걸로
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("onActivityResult", "onActivityResult: .");

        if(resultCode == Activity.RESULT_OK) {

            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

            // 출력 : No.123 (앞에 세자리를 지워버리자)
            String result = scanResult.getContents();
            String result2 = scanResult.getFormatName();

            // - 문자열 자르기, 특정 문자 기준으로 가져가기

//            // 먼저 @ 의 인덱스를 찾는다 - 인덱스 값: 5
//            int idx = result.indexOf(".");
//
//            // 뒷부분을 추출
//            // 아래 substring은 . 바로 뒷부분인 n부터 추출된다.
//            String backend = result.substring(idx+1).trim();

            value = result;
            type = result2;

            Log.d("onActivityResult", "onActivityResult: ." + result);

        }else if(resultCode == Activity.RESULT_CANCELED){

            // 리스트 초기화
            finish();

        }else if(requestCode == Activity.RESULT_FIRST_USER) {

            value = getString(R.string.text_value);
            type = getString(R.string.text_type);
        }

    }


}
