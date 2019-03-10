package com.practice.bacodescanner.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Collection;
import java.util.Collections;


public class ScannerInitActivity extends BaseActivity {

    public static final Collection<String> CODE_39_TYPE = Collections.singleton("CODE_39");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onResume(){
        super.onResume();

        IntentIntegrator integrator = new IntentIntegrator(this);

        // 액티비티 실행
        integrator.setCaptureActivity(ScannerProcessActivity.class);

        // 바코드 타입 고정 : QR type 변경
        integrator.setDesiredBarcodeFormats(integrator.QR_CODE_TYPES);
        integrator.initiateScan(integrator.QR_CODE_TYPES);

    }

    // CODE_39
    // 숫자만 건지는걸로
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("onActivityResult", "onActivityResult: .");

        if(resultCode == Activity.RESULT_OK) {

            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

            // 출력 : No.123 (앞에 세자리를 지워버리자)
            String result = scanResult.getContents();

            // - 문자열 자르기, 특정 문자 기준으로 가져가기

//            // 먼저 @ 의 인덱스를 찾는다 - 인덱스 값: 5
//            int idx = result.indexOf(".");
//
//            // 뒷부분을 추출
//            // 아래 substring은 . 바로 뒷부분인 n부터 추출된다.
//            String backend = result.substring(idx+1).trim();

            String backend = result;
            value = result;

            Log.d("onActivityResult", "onActivityResult: ." + result);

        }else if(resultCode == Activity.RESULT_CANCELED){

            // 리스트 초기화
            finish();

        }

    }


}
