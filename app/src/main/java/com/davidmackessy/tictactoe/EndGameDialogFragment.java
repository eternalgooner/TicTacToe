package com.davidmackessy.tictactoe;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by David on 25-Jul-17.
 */

public class EndGameDialogFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String result = bundle.getString("result");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(result)
                .setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("", "clicked positive button in dialog");
                        getActivity().finish();
                        getActivity().startActivity(getActivity().getIntent());
                    }
                })
                .setNegativeButton("Return to Home", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("", "clicked negative button in dialog");
                        getActivity().finish();
                    }
                });
        return builder.create();
    }
}
