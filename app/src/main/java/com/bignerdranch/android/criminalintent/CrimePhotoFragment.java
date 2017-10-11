package com.bignerdranch.android.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by MA on 11/10/2017.
 */

public class CrimePhotoFragment extends DialogFragment {

    private static final String ARG_PHOTO_PATH = "photo_path";

    private ImageView mPhotoView;

    public static CrimePhotoFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PHOTO_PATH, path);

        CrimePhotoFragment fragment = new CrimePhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String path = (String) getArguments().getSerializable(ARG_PHOTO_PATH);
        if (path != null) {
            File fileDir = getContext().getApplicationContext().getFilesDir();
            File photoFile = new File(fileDir, path);

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo, null);

            mPhotoView = (ImageView) view.findViewById(R.id.crime_photo_view);
            Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);

            return new AlertDialog.Builder(getActivity()).setView(view).create();
        } else {
            return super.onCreateDialog(savedInstanceState);
        }
    }
}
