package edu.bluejack22_2.nitip.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
    private ImageView ivProfile;
    private View view;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private LoginViewModel loginViewModel;
    private UserViewModel userViewModel;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

//        initialize(view);
//        setUser();
//        setListener(view);

        return view;
    }

//    private void setUser() {
//        userViewModel.getUser(firebaseUser.getEmail()).observe(getViewLifecycleOwner(), user -> {
//            if (user != null) {
//                this.user = new User(user.getUsername(), user.getEmail(), "", user.getProfile());
//                setProfilePicture(view);
//            }
//        });
//    }

//    private void initialize(View view) {
//        ivProfile = view.findViewById(R.id.ivProfilePicture);
//        gso = GoogleService.getGso(view.getContext());
//        gsc = GoogleService.getGsc(view.getContext());
//        loginViewModel = new LoginViewModel();
//        userViewModel = new UserViewModel();
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();
//    }
//    private void setListener(View view) {
//
//        btnLogout.setOnClickListener(e -> {
//            gsc.signOut().addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    loginViewModel.googleLogout();
//                    Toast.makeText(view.getContext(), "Signed Out", Toast.LENGTH_SHORT).show();
//                }
//            });
//            ActivityChanger.changeActivity(view.getContext(), LoginActivity.class);
//        });
//    }

//    private void setProfilePicture(View view) {
//        ImageView imageView = view.findViewById(R.id.ivProfilePicture);
//        Glide.with(view.getContext())
//                .load(user.getProfile())
//                .into(imageView);
//        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/nitip-6f389.appspot.com/o/profile_pictures%2Fsuperxyber123%40gmail.com?alt=media&token=522db4c1-2ac6-45a2-8c0d-7b1b71a0950f";
//        String imageUrl = this.user.getProfile();
//        new DownloadImageTask(imageView).execute(imageUrl);
//    }
}