package com.zjq.androidpermissionstest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by zjq on 2016/9/27.
 */

public class CameraFragment extends Fragment {

  private final int REQUEST_CODE_CAMERA = 0x001;

  private Intent mCameraIntent;
  private ImageView mImageView;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_camera, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    mCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    mImageView = (ImageView) view.findViewById(R.id.imageView);
    view.findViewById(R.id.takePhoto).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        PermissionUtil.startActivityForResultWithPermisssions(getActivity(), mCameraIntent,
            REQUEST_CODE_CAMERA, Manifest.permission.CAMERA,
            PermissionUtil.RequestPermissionCode.CAMERA, null);
      }
    });
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case PermissionUtil.RequestPermissionCode.CAMERA: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          startActivityForResult(mCameraIntent, REQUEST_CODE_CAMERA);
        } else {
          Toast.makeText(getActivity(), "用户取消授权", Toast.LENGTH_LONG).show();
        }
      }
    }
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == Activity.RESULT_OK) {
      switch (requestCode) {
        case REQUEST_CODE_CAMERA:
          if (data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(bitmap);
          }
          break;
      }
    }
  }
}
