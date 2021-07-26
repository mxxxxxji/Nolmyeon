package com.example.nolmyeon;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.example.nolmyeon.activity.CustomQRScanActivity;
import com.example.nolmyeon.activity.MainActivity;

public class CustomDialog3 {
    private Context context;

    public CustomDialog3(Context context)
    {

        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(String[] results) {

        Log.d("TAG_qr",results.toString());
        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog3);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final ImageButton okButton = dlg.findViewById(R.id.ok_btn);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), CustomQRScanActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
                ((CustomQRScanActivity)context).finish();

            }

        });
    }
}
