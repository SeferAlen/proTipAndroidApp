package com.example.protip.utility;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.protip.R;

public class ProTipDialog {

    public static void openDialog(final String title, final String text, final DialogType dialogType, AppCompatActivity activity) {
        if (title == null) throw new NullPointerException("title must not be null");
        if (text == null) throw new NullPointerException("text must not be null");
        if (dialogType == null) throw new NullPointerException("dialogType must not be null");
        if (activity == null) throw new NullPointerException("activity must not be null");

        final ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View dialogView;
        if (dialogType.equals(DialogType.INFO)) {
            dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_info, viewGroup, false);
        } else if (dialogType.equals(DialogType.ERROR)) {
            dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_error, viewGroup, false);
        } else  {
            dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_info, viewGroup, false);
        }

        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        final Button buttonOK = (Button) dialogView.findViewById(R.id.buttonOk);
        TextView textViewTitle = (TextView) dialogView.findViewById(R.id.editTextTitle);
        TextView textViewMessage = (TextView) dialogView.findViewById(R.id.editTextText);

        textViewTitle.setText(title);
        textViewMessage.setText(text);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });
    }
}
