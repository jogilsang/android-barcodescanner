package com.practice.bacodescanner.activity;

import android.app.Activity;

import java.util.ArrayList;


public class BaseActivity extends Activity {

    public static ArrayList<String> entries = new ArrayList<String>();
    public static String value = "Value";
    public static String type = "Barcode Type";

    public static String QR_CODE   = "QR_CODE";
    public static String DATA_MATRIX   = "DATA_MATRIX";
    public static String UPC_A   = "UPC_A";
    public static String UPC_E   = "UPC_E";
    public static String EAN_8   = "EAN_8";

    public static String EAN_13   = "EAN_13";
    public static String CODE_39   = "CODE_39";
    public static String  CODE_93  = "CODE_93";
    public static String  CODE_128  = "CODE_128";
    public static String  ITF  = "ITF";

    public static String  RSS_14  = "RSS_14";
    public static String  RSS_EXPANDED  = "RSS_EXPANDED";

    public static int choice = 0;

    // 출석체크를 누른다
    // 화면이 켜짐과 동시에 액셀파일을 만들고 파일명을 넘겨준다
    // 출석체크를 시작한다.
    // 바코드를 인식한후 액셀파일과 대조해서 넣는다.
    // 백버튼 또는 출석 종료 버튼을 눌러서 나온다
    // 리스트를 보여준다
    // 서버전송, 추가 진행, 종료

}
