package Fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.example.emrsupportapp.activities.DatabaseHelper;
import com.example.emrsupportapp.activities.FaqTodo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddFaqFragment extends Fragment {
    private ImageView ivImageCapture, ivVideoCapture;
    private EditText etFaqTitle, etFaqDescription;
    private Button btnSubmit;
    //Image
    private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 1;
    private static final int REQUEST_IMAGE_PICKER_PERMISSION = 2;

    //Video
    private static final int REQUEST_VIDEO_CAPTURE_PERMISSION = 3;
    private static final int REQUEST_VIDEO_PICKER_PERMISSION = 2;
    private static final String TAG = "TAG";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_faq, container, false);

        ivImageCapture = view.findViewById(R.id.ivImageCapture);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        etFaqTitle = view.findViewById(R.id.etFaqTitle);
        etFaqDescription = view.findViewById(R.id.etFaqDescription);

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
                String title = etFaqTitle.getText().toString();
                String desc = etFaqDescription.getText().toString();
                FaqTodo todo = new FaqTodo(title, desc);
                AsyncTaskTodo asyncTaskTodo = new AsyncTaskTodo();
                asyncTaskTodo.execute(todo);
                Log.i(TAG, "onClick: " + todo.toString());
                Toast.makeText(getActivity(), "title: " + title + "\n" + "Desc: " + desc, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
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

    public void askForCameraPermissionImage() {
        //Permission not Granted
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
                                REQUEST_IMAGE_CAPTURE_PERMISSION);
                    }
                });
                builder.create();
                builder.show();
            } else {
                //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
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
                    /*Uri selectedImage = data.getData();
                    ivImageCapture.setImageURI(selectedImage);*/
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ivImageCapture.setImageBitmap(bitmap);
                    Log.i(TAG, "RESULT_OK");
                    //saveImage();
                    break;
                }
                break;
            case REQUEST_IMAGE_PICKER_PERMISSION:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    ivImageCapture.setImageURI(selectedImage);
                    Log.i(TAG, "RESULT_OK");
                }
                break;
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

    public void saveImage() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ivImageCapture.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        FileOutputStream outputStream = null;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath() + "/EyeSmartSupportApp");
        dir.mkdir();
        String fileName = String.format("%d.png", System.currentTimeMillis());
        File outFile = new File(dir, fileName);
        try {
            outputStream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
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
                    //To take picture from camera
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE_PERMISSION);
                    //saveImage();
                } else if (options[which].equals("Choose from Gallery")) {
                    //To pick photo from gallery
                    Intent imagePicker = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imagePicker, REQUEST_IMAGE_PICKER_PERMISSION);
                } else if (options[which].equals("View Image")) {
                    startActivity(new Intent(getActivity(), ImageActivity.class));
                }
            }
        });
        builder.create();
        builder.show();
    }

    private void selectVideo() {
        final CharSequence[] options = {"Take Video", "Choose from Gallery", "Cancel"};
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
                } else if (options[which].equals("Cancel")) {
                    dialog.dismiss();
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