package org.nganga.furl.userInfo;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.digits.sdk.android.Digits;
import com.makeramen.roundedimageview.RoundedImageView;
import com.parse.ParseUser;

import org.nganga.furl.communicationInfrastructure.FurlMain;
import org.nganga.furl.R;
import org.nganga.furl.authenticateActivity.InitialActivity;


public class AccountSettings extends Activity {

    private Button logOutBtn;
    private Button saveSettingsBtn;
    final ParseUser user = ParseUser.getCurrentUser();
    private TextInputLayout mUsernameLayout;
    private TextInputLayout mStatusLayout;
    private EditText mUsernameText;
    private EditText mStatusText;
    private static final int REQUEST_CAMERA = 1888;
    private static final int SELECT_FILE = 1888;
    private RoundedImageView ivImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setUpViews();

        ivImage = (RoundedImageView) findViewById(R.id.profile_photo);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();
            }

        });

        mStatusLayout = (TextInputLayout) findViewById(R.id.status_text_layout);
        mStatusText = (EditText) findViewById(R.id.status_text);
        mUsernameLayout = (TextInputLayout) findViewById(R.id.user_name_text_layout);
        mUsernameText = (EditText) findViewById(R.id.user_name_text);

        logOutBtn = (Button) findViewById(R.id.logOutBtn);
        saveSettingsBtn = (Button) findViewById(R.id.saveSettingsBtn);

        saveSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserSettings();
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AccountSettings.this);
                builder.setMessage(R.string.confirm_delete);
                builder.setTitle("Confirm Account DEACTIVATION");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        deleteSession();

                        //to close the dialog
                        dialogInterface.dismiss();
                    }
                });


                 builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //to close the dialog
                        dialogInterface.dismiss();
                    }
                });


                AlertDialog  dialog = builder.create();
                dialog.show();
            }


        });

    }

    private void setUpViews() {
        setUpBack();

    }


    private void setUpBack() {
        // go back if clicked
        final ImageView backButton = (ImageView) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setUpCount() {
        // go back if clicked
        final ImageView backButton = (ImageView) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void saveUserSettings(){

        boolean isUsernameEmpty = userNameEmpty();
        boolean isStatusEmpty = statusInputEmpty();

        if (isStatusEmpty && isUsernameEmpty){

            Context context = getApplicationContext();
            CharSequence text = "All The Fields!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else if (isStatusEmpty && !isUsernameEmpty) {

            mStatusLayout.setError("Whats On Your Mind");
            mUsernameLayout.setError(null);
        }
        else if (!isStatusEmpty && isUsernameEmpty) {

            mUsernameLayout.setError("You Used To Have A Name");
            mStatusLayout.setError(null);
        }
        else {

            user.put("username", mUsernameText.getText().toString());
            user.put("status", mStatusText.getText().toString());
            user.saveEventually();
            final Intent intent = new Intent(AccountSettings.this,
                    FurlMain.class);
            startActivity(intent);
        }



    }

    private boolean userNameEmpty(){

        return mUsernameText.getText() == null || mUsernameText.getText().toString() == null || mUsernameText.getText().toString().isEmpty();
    }


    private boolean statusInputEmpty(){

        return mStatusText.getText() == null || mStatusText.getText().toString() == null || mStatusText.getText().toString().isEmpty();
    }

    public void deleteSession(){

        Digits.getSessionManager().clearActiveSession();
        user.put("installed", false);
        user.saveEventually();

        final Intent intent = new Intent(AccountSettings.this,
                InitialActivity.class);
        startActivity(intent);

    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AccountSettings.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/* video/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ivImage.setImageBitmap(thumbnail);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);

                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                ivImage.setImageBitmap(bm);
                cursor.close();
            }
        }
    }


}
