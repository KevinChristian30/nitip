package edu.bluejack22_2.nitip.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Facade.DownloadImageTask;
import edu.bluejack22_2.nitip.Model.User;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Service.GoogleService;
import edu.bluejack22_2.nitip.View.HomeActivity;
import edu.bluejack22_2.nitip.View.LoginActivity;
import edu.bluejack22_2.nitip.ViewModel.LoginViewModel;
import edu.bluejack22_2.nitip.ViewModel.UserViewModel;

public class ProfileFragment extends Fragment {
    private User user;
    private Button btnLogout;
    private Button btnChangeProfilePicture;
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
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_REQUEST = 100;
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
        gso = GoogleService.getGso(view.getContext());
        gsc = GoogleService.getGsc(view.getContext());
        loginViewModel = new LoginViewModel();
        userViewModel = new UserViewModel();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
            }
        });
    }
    private void setListener(View view) {

        btnLogout.setOnClickListener(e -> {
            gsc.signOut().addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loginViewModel.googleLogout();
                    Toast.makeText(view.getContext(), "Signed Out", Toast.LENGTH_SHORT).show();
                }
            });
            ActivityChanger.changeActivity(view.getContext(), LoginActivity.class);
        });

        btnChangeProfilePicture.setOnClickListener(e -> {
            if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Request permission
                System.out.println("clicked");
//                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                requestPermissions(
                        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                        100);
            } else {
                // Permission is already granted
            }

        });
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

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

}