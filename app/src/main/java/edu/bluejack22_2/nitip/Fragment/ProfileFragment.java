package edu.bluejack22_2.nitip.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Facade.DownloadImageTask;
import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Service.GoogleService;
import edu.bluejack22_2.nitip.View.LoginActivity;
import edu.bluejack22_2.nitip.View.OTPForChangePasswordActivity;
import edu.bluejack22_2.nitip.ViewModel.LoginViewModel;
import edu.bluejack22_2.nitip.ViewModel.UserViewModel;

public class ProfileFragment extends Fragment {
    private User user;
    private Button btnLogout;
    private Button btnChangeProfilePicture;
    private Button btnChangePassword;
    private ImageView ivProfile;
    private TextView tvUsername;
    private TextView tvEmail;
    private View view;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private LoginViewModel loginViewModel;
    private UserViewModel userViewModel;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private final ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    if (data != null) {
                        Uri imageUri = data.getData();
                        userViewModel.changeProfilePicture(imageUri);

                    }
                }
            });


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initialize(view);
        setUser();
        setListener(view);

        return view;
    }

    private void setUser() {
        userViewModel.getUser(firebaseUser.getEmail()).observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                this.user = new User(user.getUsername(), user.getEmail(), "", user.getProfile());
                setProfilePicture(view);
                setInformation(view);
            }
        });
    }

    private void initialize(View view) {
        ivProfile = view.findViewById(R.id.ivProfilePicture);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnChangeProfilePicture = view.findViewById(R.id.btnChangeProfilePicture);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        gso = GoogleService.getGso(view.getContext());
        gsc = GoogleService.getGsc(view.getContext());
        loginViewModel = new LoginViewModel();
        userViewModel = new UserViewModel();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    private void setListener(View view) {
        btnLogout.setOnClickListener(e -> {
            gsc.signOut().addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loginViewModel.googleLogout();
                    Toast.makeText(view.getContext(), "Signed Out", Toast.LENGTH_SHORT).show();
                    requireActivity().finish();
                }
            });
            ActivityChanger.changeActivity(view.getContext(), LoginActivity.class);
        });

        btnChangeProfilePicture.setOnClickListener(e -> {
            if (ContextCompat.checkSelfPermission(view.getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();

                triggerNotification();


            } else {
                requestStoragePermission();
            }
        });

        btnChangePassword.setOnClickListener(e -> {
            ActivityChanger.changeActivity(view.getContext(), OTPForChangePasswordActivity.class);
        });
    }

    private void triggerNotification() {

        Context context = getActivity();

        if (context != null) {
            // Create a notification channel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "MyNotificationChannel";
                String description = "Channel for My App";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("notifyId", name, importance);
                channel.setDescription(description);

                // Register the channel with the system
                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }

            // Create a notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyId")
                    .setSmallIcon(R.drawable.add_titip_detail_activity)  // Set the icon
                    .setContentTitle("Profile Update")  // Set the title
                    .setContentText("Your profile updated!")  // Set the text
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            // Get the NotificationManager
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

            // Send the notification
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationManagerCompat.notify(1, builder.build());
//            notificationManagerCompat.notify(1, builder.build());
        }

    }


    private void requestStoragePermission() {

        new AlertDialog.Builder(view.getContext())
                .setTitle("Permission needed")
                .setMessage("You need to grant external storage permission to change profile picture")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(view.getContext(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        someActivityResultLauncher.launch(intent);
    }



    private void setProfilePicture(View view) {
        Glide.with(view.getContext())
                .load(user.getProfile())
                .into(ivProfile);
        String imageUrl = this.user.getProfile();
        new DownloadImageTask(ivProfile).execute(imageUrl);
    }

    private void setInformation(View view) {
        tvUsername.setText(user.getUsername());
        tvEmail.setText(user.getEmail());
    }


}