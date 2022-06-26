package com.kingdew.ohshazadreport;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.widget.ProgressBar;

public class LoadingDialog {

    Context context;
    Dialog dialog;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void startDialog(){
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar bar=dialog.findViewById(R.id.progressBar);
        //bar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        dialog.show();

    }

    public void stopDialog(){
        dialog.dismiss();
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }

}
