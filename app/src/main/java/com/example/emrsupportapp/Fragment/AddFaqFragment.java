package com.example.emrsupportapp.Fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.emrsupportapp.ImageActivity;
import com.example.emrsupportapp.R;
import com.example.emrsupportapp.VideoActivity;
import com.example.emrsupportapp.activities.DatabaseHelper;
import com.example.emrsupportapp.activities.FaqTodo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddFaqFragment extends Fragment {
    private ImageView ivImageCapture, ivVideoCapture;
    private EditText etFaqTitle, etFaqDescription;
    private Button btnSubmit;
    //Image
    private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 1;
    private static final int REQUEST_IMAGE_PICKER_PERMISSION = 2;

    //Video
    private static final int REQUEST_VIDEO_CAPTURE_PERMISSION = 3;
    private static final int REQUEST_VIDEO_PICKER_PERMISSION = 4;

    private static final int EXTERNAL_STORAGE_PERMISSION_CODE = 5;
    private static final String TAG = "TAG";

    Bitmap bitmap;
    String currentPhotoPath;
    Uri imageUri;
    String filename = "imageFile";
    String directoryName = "EyeSmartSupportApp";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_faq, container, false);
        ivImageCapture = view.findViewById(R.id.ivImageCapture);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        etFaqTitle = view.findViewById(R.id.etFaqTitle);
        etFaqDescription = view.findViewById(R.id.etFaqDescription);
        etFaqTitle.addTextChangedListener(SubmitTextWatcher);
        etFaqDescription.addTextChangedListener(SubmitTextWatcher);

        ivImageCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForCameraPermissionImage();
            }
        });
        ivVideoCapture = view.findViewById(R.id.ivVideoCapture);
        ivVideoCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForCameraPermissionVideo();
            }
        });
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createDirectory("SaveImageDir");
                String title = etFaqTitle.getText().toString();
                String desc = etFaqDescription.getText().toString();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                FaqTodo todo = new FaqTodo(title, desc, date);
                AsyncTaskTodo asyncTaskTodo = new AsyncTaskTodo();
                asyncTaskTodo.execute(todo);
                Log.i(TAG, "onClick: " + todo.toString());
                Toast.makeText(getActivity(), "Successfully Saved", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    /*private File createDirectory(String dirName) {
        File file = new File(getContext().getExternalFilesDir(null) + "/" + dirName);
        if (!file.exists()) {
            file.mkdir();
            Toast.makeText(getActivity(), "File Created", Toast.LENGTH_SHORT).show();
        }
        return file;
    }*/
    private void saveImageToGallery(Bitmap bitmap) {
        OutputStream fos;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = getContext().getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename + ".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + directoryName);
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);
                Toast.makeText(getActivity(), "Image Saved", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Image Not Found" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private TextWatcher SubmitTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String title = etFaqTitle.getText().toString().trim();
            String desc = etFaqDescription.getText().toString().trim();
            btnSubmit.setEnabled(!title.isEmpty() && !desc.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void askForCameraPermissionVideo() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //explanation to the user why we need this permission.
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)
                    &&
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Permission Required");
                builder.setMessage("You need to give Permission to use this Camera");
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("GRANT PERMISSION", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_VIDEO_CAPTURE_PERMISSION);
                    }
                });
                builder.create();
                builder.show();
            } else {
                //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_VIDEO_CAPTURE_PERMISSION);
            }
        } else {
            //Permission already Granted
            Toast.makeText(getActivity(), "Permission already Granted", Toast.LENGTH_SHORT).show();
            selectVideo();
        }
    }

    public void askForCameraPermissionImage() {
        //Permission not Granted
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //explanation to the user why we need this permission.
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)
                    &&
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    &&
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Permission Required");
                builder.setMessage("You need to give Permission to use this Camera");
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("GRANT PERMISSION", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_IMAGE_CAPTURE_PERMISSION);
                    }
                });
                builder.create();
                builder.show();
            } else {
                //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_IMAGE_CAPTURE_PERMISSION);
            }
        } else {
            //Permission already Granted
            Toast.makeText(getActivity(), "Permission already Granted", Toast.LENGTH_SHORT).show();
            selectImage();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission Given", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Permission Required");
                        builder.setMessage("Permission has been Denied! You need to give Permission");
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setPositiveButton("GO TO SETTING", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
                                startActivity(intent);
                            }
                        });
                        builder.create();
                        builder.show();
                    } else {
                        Toast.makeText(getActivity(), "Permission Denied Permanently", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE_PERMISSION:
                if (resultCode == RESULT_OK) {
                    /*Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                    ivImageCapture.setImageBitmap(bitmap);*/
                    bitmap = (Bitmap) data.getExtras().get("data");
                    ivImageCapture.setImageBitmap(bitmap);
                    saveImageToGallery(bitmap);
                    Log.i(TAG, "RESULT_OK");
                    break;
                }
                break;
            /*case REQUEST_IMAGE_PICKER_PERMISSION:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    ivImageCapture.setImageURI(selectedImage);
                    Log.i(TAG, "RESULT_OK");
                }
                break;*/
        }
        /*if (requestCode == REQUEST_IMAGE_CAPTURE_PERMISSION) {
            switch (resultCode) {
                case RESULT_OK:
                    Log.i(TAG, "RESULT_OK");
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ivImageCapture.setImageBitmap(bitmap);
                    break;
                case RESULT_CANCELED:
                    Log.i(TAG, "RESULT_CANCELED");
                    break;
            }
        } else if (requestCode == REQUEST_CAMERA_PERMISSION_VIDEO && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            videoActivity.videoView.setVideoURI(uri);
            videoActivity.videoView.start();
        }*/
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "View Image"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE_PERMISSION);
                } else if (options[which].equals("Choose from Gallery")) {
                    //To pick photo from gallery
                    Intent imagePicker = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imagePicker, REQUEST_IMAGE_PICKER_PERMISSION);
                } else if (options[which].equals("View Image")) {
                    Intent intent = new Intent(getActivity(), ImageActivity.class);
                    intent.putExtra("bitmap", bitmap);
                    getActivity().startActivity(intent);
                }
            }
        });
        builder.create();
        builder.show();
    }

    private void selectVideo() {
        final CharSequence[] options = {"Take Video", "Choose from Gallery", "View"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Video!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Take Video")) {
                    //To take video from camera
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(intent, REQUEST_VIDEO_CAPTURE_PERMISSION);
                } else if (options[which].equals("Choose from Gallery")) {
                    //To pick video from gallery
                    Intent imagePicker = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imagePicker, REQUEST_VIDEO_PICKER_PERMISSION);
                } else if (options[which].equals("View")) {
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    startActivity(intent);
                }
            }
        });
        builder.create();
        builder.show();
    }

    public class AsyncTaskTodo extends AsyncTask<FaqTodo, Void, Void> {

        @Override
        protected Void doInBackground(FaqTodo... faqTodos) {
            DatabaseHelper.getInstance(getActivity()).todoDao().insertTodo(faqTodos[0]);
            return null;
        }
    }

}