package com.example.emrsupportapp.Fragment.RaisedTickets;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emrsupportapp.BuildConfig;
import com.example.emrsupportapp.ImageActivity;
import com.example.emrsupportapp.R;
import com.example.emrsupportapp.VideoActivity;
import com.example.emrsupportapp.activities.DatabaseHelper;
import com.example.emrsupportapp.activities.TicketTodo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class AddTicket_Fragment extends Fragment {
    private final TicketTodo ticketTodo;

    public AddTicket_Fragment(TicketTodo ticketTodo) {
        this.ticketTodo = ticketTodo;
    }

    private static final String TAG = "TAG";
    EditText etTicketTitle, etTicketDescription;
    ImageView ivImageCaptureTicket, ivVideoCaptureTicket;
    Button btnSubmitTicket;
    Bitmap bitmap;
    Uri imageUri;
    Uri videoUri;
    String filename = "imageFile";
    String directoryName = "EyeSmartSupportApp";
    String selectedVideoPath;
    String selectedImagePath;
    //Image
    private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 1;
    private static final int REQUEST_IMAGE_PICKER_PERMISSION = 2;
    //Video
    private static final int REQUEST_VIDEO_CAPTURE_PERMISSION = 3;
    private static final int REQUEST_VIDEO_PICKER_PERMISSION = 4;
    //Edit
    LinearLayout llTicketStatus;
    EditText etTicketSolution;
    TextView txtTicketSolution;
    Spinner ticketStatusSpinner;
    TicketTodo todo;
    String currentImagePath = null;
    File imageFile = null;
    private String imagePath = "";
    Bundle bundle;
     String moduleType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_ticket, container, false);
        etTicketTitle = view.findViewById(R.id.etTicketTitle);
        etTicketTitle.addTextChangedListener(SubmitTextWatcher);
        etTicketDescription = view.findViewById(R.id.etTicketDescription);
        etTicketDescription.addTextChangedListener(SubmitTextWatcher);
        ivImageCaptureTicket = view.findViewById(R.id.ivImageCaptureTicket);
        ivVideoCaptureTicket = view.findViewById(R.id.ivVideoCaptureTicket);
        btnSubmitTicket = view.findViewById(R.id.btnSubmitTicket);
        //Edit
        llTicketStatus = view.findViewById(R.id.llTicketStatus);
        etTicketSolution = view.findViewById(R.id.etTicketSolution);
        txtTicketSolution = view.findViewById(R.id.txtTicketSolution);
        ticketStatusSpinner = view.findViewById(R.id.ticketStatusSpinner);
        bundle = new Bundle();
        bundle = this.getArguments();
        if (bundle != null) {
            moduleType = bundle.getString("moduleType");
        }
        btnSubmitTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ticketTodo == null) {
                    String title = etTicketTitle.getText().toString();
                    String desc = etTicketDescription.getText().toString();
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    todo = new TicketTodo(title, moduleType, desc, currentDate, currentTime, imagePath, selectedVideoPath, null, null);
                    AsyncTaskTodo asyncTaskTodo = new AsyncTaskTodo();
                    asyncTaskTodo.execute(todo);
                    Log.i(TAG, "onClick: " + todo.toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Success !!");
                    builder.setMessage("Data saved successfully...");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getFragmentManager().popBackStack();
                        }
                    });
                    builder.create().show();
                } else {
                    updateATodo();
                }
            }
        });
        ivImageCaptureTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForCameraPermissionImage();
            }
        });
        ivVideoCaptureTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForCameraPermissionVideo();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ticketTodo != null) {
            initEdit();
        }
    }

    private void initEdit() {
        String image = ticketTodo.getImagesUrl();
        String video = ticketTodo.getVideoUrl();
        String status = ticketTodo.getTicketStatus();
        String solution = ticketTodo.getTicketSolution();
        String[] baths = getResources().getStringArray(R.array.ticketStatus);
        ticketStatusSpinner.setSelection(Arrays.asList(baths).indexOf(status));
        llTicketStatus.setVisibility(View.VISIBLE);
        etTicketSolution.setVisibility(View.VISIBLE);
        txtTicketSolution.setVisibility(View.VISIBLE);
        etTicketTitle.setText(ticketTodo.getTitle());
        etTicketTitle.setEnabled(false);
        etTicketDescription.setText(ticketTodo.getDescription());
        etTicketDescription.setEnabled(false);
        if (solution != null) {
            etTicketSolution.setText(solution);
        }
        if (image != null && !image.isEmpty()) {
            ivImageCaptureTicket.setImageURI(Uri.parse(image));

        }
        if (video != null) {
            ivVideoCaptureTicket.setImageBitmap(createVideoThumbnail(video));
        }
    }

    public Bitmap createVideoThumbnail(String path) {
        return ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);
    }

    public class AsyncTaskTodo extends AsyncTask<TicketTodo, Void, Void> {
        @Override
        protected Void doInBackground(TicketTodo... ticketTodos) {
            DatabaseHelper.getInstance(getActivity()).todoDao().insertTodoTicket(ticketTodos[0]);
            return null;
        }
    }

    public void updateATodo() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                TicketTodo todo = DatabaseHelper.getInstance(getActivity()).todoDao().getTodoById(ticketTodo.getUid());
                String solution = etTicketSolution.getText().toString();
                String spinner = ticketStatusSpinner.getSelectedItem().toString();
                todo.setTicketSolution(solution);
                todo.setTicketStatus(spinner);
                if (todo != null) {
                    DatabaseHelper.getInstance(getActivity()).todoDao().updateTodo(todo);
                    Log.i(TAG, "updateATodo: " + todo.toString());
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Success !!");
        builder.setMessage("Data saved successfully...");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().popBackStack();
            }
        });
        builder.create().show();
        thread.start();
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

    private TextWatcher SubmitTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String title = etTicketTitle.getText().toString().trim();
            String desc = etTicketDescription.getText().toString().trim();
            btnSubmitTicket.setEnabled(!title.isEmpty() && !desc.isEmpty());
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
                    /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //Here image URI should not be null. It should be path where we supposed to save Image captures from camera
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE_PERMISSION);*/
                    captureImage();
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

    public void captureImage() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            try {
                imageFile = getImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (imageFile != null) {
                Uri imageUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                this.startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE_PERMISSION);
            }
        }
    }

    private File getImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_" + timeStamp + ".jpg";
        File folder = new File(Environment.getExternalStorageDirectory() + "/DCIM/EyeSmartSupportApp/2022/Images");
        if (!folder.exists())
            folder.mkdirs();
        File imageFile = new File(Environment.getExternalStorageDirectory() + "/DCIM/EyeSmartSupportApp/2022/Images/" + imageName);
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
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
                if (resultCode == Activity.RESULT_OK && imageFile.exists()) {
                    /*bitmap = (Bitmap) data.getExtras().get("data");
                    saveImageToGallery(bitmap);
                    ivImageCaptureTicket.setImageBitmap(bitmap);
                    selectedImagePath = String.valueOf(getImageUri(getActivity(), bitmap));
                    Log.i(TAG, "RESULT_OK");*/
                    /*Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        ivImageCaptureTicket.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    imagePath = imageFile.getAbsolutePath(); //imagePath save this is db
                    ivImageCaptureTicket.setImageBitmap(bitmap);
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
                        ivImageCaptureTicket.setImageBitmap(bitmap);
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
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }
}