package com.example.emrsupportapp.Fragment.TrainingModule;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import com.example.emrsupportapp.Fragment.FaqModule.AddFaqFragment;
import com.example.emrsupportapp.ImageActivity;
import com.example.emrsupportapp.R;
import com.example.emrsupportapp.VideoActivity;
import com.example.emrsupportapp.activities.DatabaseHelper;
import com.example.emrsupportapp.activities.FaqTodo;
import com.example.emrsupportapp.activities.TrainingTodo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class AddTraining_Fragment extends Fragment {
    //Image
    private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 1;
    private static final int REQUEST_IMAGE_PICKER_PERMISSION = 2;

    //Video
    private static final int REQUEST_VIDEO_CAPTURE_PERMISSION = 3;
    private static final int REQUEST_VIDEO_PICKER_PERMISSION = 4;
    private static final String TAG = "TAG";
    EditText etTrainingTitle, etTrainingDescription;
    ImageView ivImageCaptureTraining, ivVideoCaptureTraining;
    Button btnSubmitTraining;
    Bitmap bitmap;
    Uri imageUri;
    Uri videoUri;
    String filename = "imageFile";
    String directoryName = "EyeSmartSupportApp";
    String selectedImagePath;
    String selectedVideoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_training, container, false);
        etTrainingTitle = view.findViewById(R.id.etTrainingTitle);
        etTrainingTitle.addTextChangedListener(SubmitTextWatcher);
        etTrainingDescription = view.findViewById(R.id.etTrainingDescription);
        etTrainingDescription.addTextChangedListener(SubmitTextWatcher);
        ivImageCaptureTraining = view.findViewById(R.id.ivImageCaptureTraining);
        ivVideoCaptureTraining = view.findViewById(R.id.ivVideoCaptureTraining);
        btnSubmitTraining = view.findViewById(R.id.btnSubmitTraining);
        btnSubmitTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trainingTitle = etTrainingTitle.getText().toString();
                String trainingDesc = etTrainingDescription.getText().toString();
                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                TrainingTodo trainingTodo = new TrainingTodo(trainingTitle, trainingDesc, currentDate, currentTime, selectedImagePath, selectedVideoPath);
                AsyncTaskTodoTraining asyncTaskTodoTraining = new AsyncTaskTodoTraining();
                asyncTaskTodoTraining.execute(trainingTodo);
                Log.i(TAG, "onClick: " + trainingTodo.toString());
                Toast.makeText(getActivity(), "Successfully Saved", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        });
        ivImageCaptureTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForCameraPermissionImage();
            }
        });
        ivVideoCaptureTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForCameraPermissionVideo();
            }
        });
        return view;
    }

    private TextWatcher SubmitTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String title = etTrainingTitle.getText().toString().trim();
            String desc = etTrainingDescription.getText().toString().trim();
            btnSubmitTraining.setEnabled(!title.isEmpty() && !desc.isEmpty());
        }
    };

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

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "View Image"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Take Photo")) {
                    /*File file = new File(String.valueOf(photoFile));      //1 image is not good while saving
                    Uri imageUri = Uri.fromFile(file);*/
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //Here image URI should not be null. It should be path where we supposed to save Image captures from camera
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
                    Intent videoPicker = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(videoPicker, REQUEST_VIDEO_PICKER_PERMISSION);
                } else if (options[which].equals("View")) {
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    intent.putExtra("uri", videoUri.toString());
                    startActivity(intent);
                }
            }
        });
        builder.create();
        builder.show();
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
                if (resultCode == RESULT_OK && data != null) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    saveImageToGallery(bitmap);
                    ivImageCaptureTraining.setImageBitmap(bitmap);
                    selectedImagePath = String.valueOf(getImageUri(getActivity(), bitmap));
                    Log.i(TAG, "RESULT_OK");
                }
                break;
            case REQUEST_VIDEO_CAPTURE_PERMISSION:
                if (resultCode == RESULT_OK && data != null) {
                    videoUri = data.getData();
                    selectedVideoPath = getRealPathFromUri(videoUri);
                }
                break;
            case REQUEST_IMAGE_PICKER_PERMISSION:
                if (resultCode == RESULT_OK) {
                    imageUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                        selectedImagePath = String.valueOf(getImageUri(getActivity(), bitmap));
                        ivImageCaptureTraining.setImageBitmap(bitmap);
                        Log.i(TAG, "RESULT_OK");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case REQUEST_VIDEO_PICKER_PERMISSION:
                if (resultCode == RESULT_OK && data != null) {
                    videoUri = data.getData();
                    selectedVideoPath = getRealPathFromUri(videoUri);
                }
                break;
        }
    }

    public Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromUri(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    private void saveImageToGallery(Bitmap bitmap) {
        OutputStream fos;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = getContext().getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, filename + ".jpg");     //set image name
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + directoryName);
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
            } else {
                //below android Q
                String imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                File image = new File(imageDir, "eyeSmart" + ".jpg");
                fos = new FileOutputStream(image);
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            try {
                Objects.requireNonNull(fos).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getActivity(), "Image Saved", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Image Not Found" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public class AsyncTaskTodoTraining extends AsyncTask<TrainingTodo, Void, Void> {

        @Override
        protected Void doInBackground(TrainingTodo... trainingTodos) {
            DatabaseHelper.getInstance(getActivity()).todoDao().insertTodoTraining(trainingTodos[0]);
            return null;
        }
    }
}