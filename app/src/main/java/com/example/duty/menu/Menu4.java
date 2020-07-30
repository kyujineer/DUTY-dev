package com.example.duty.menu;
// main_menu = popup

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.duty.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Menu4 extends Fragment {

    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;
    public static final int REQUESTCODE = 101;

    private View view;
    private Context context;

    String mCurrentPhotoPath;
    Uri imageURI;
    Uri photoURI, albumURI;
    ImageView user_profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.menu_4, container, false);
        context = container.getContext();
        user_profile = (ImageView)view.findViewById(R.id.user_profile);
        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu pop = new PopupMenu(getActivity().getApplicationContext(), view);
                getActivity().getMenuInflater().inflate(R.menu.menu_4_popup, pop.getMenu());
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.pop_one:
                                captureCamera();
                                break;
                            case R.id.pop_two:
                                getAlbum();
                                break;
                        }
                        return true;
                    }
                });
                pop.show();
//                checkPermission();
            }
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 1) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void captureCamera() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    Uri providerUri = FileProvider.getUriForFile(context, getActivity().getPackageName(), photoFile);
                    imageURI = providerUri;

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerUri);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            } else {
                Toast.makeText(context, "접근 불가", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;
        File storageDir = new File(context.getExternalFilesDir(null), "/Pictures");

        if(!storageDir.exists()){
            storageDir.mkdirs();
        }
        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }
    private void getAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    public void cropImage(){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI,"image/*");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", albumURI);
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

//    private void galleryAddPic(){
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//    }
}


