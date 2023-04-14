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

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.Facade.DownloadImageTask;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.Service.GoogleService;
import edu.bluejack22_2.nitip.View.HomeActivity;
import edu.bluejack22_2.nitip.View.LoginActivity;
import edu.bluejack22_2.nitip.ViewModel.LoginViewModel;

public class ProfileFragment extends Fragment {

    private Button btnLogout;
    private ImageView ivProfile;
    private View view;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private LoginViewModel loginViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initialize(view);
        setListener(view);
        setProfilePicture(view);

        return view;
    }

    private void initialize(View view) {
        btnLogout = view.findViewById(R.id.btnLogout);
        ivProfile = view.findViewById(R.id.ivProfile);
        gso = GoogleService.getGso(view.getContext());
        gsc = GoogleService.getGsc(view.getContext());
        loginViewModel = new LoginViewModel();
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
    }

    private void setProfilePicture(View view) {
        ImageView imageView = view.findViewById(R.id.ivProfile);
        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/nitip-6f389.appspot.com/o/profile_pictures%2Fsuperxyber123%40gmail.com?alt=media&token=522db4c1-2ac6-45a2-8c0d-7b1b71a0950f";
        new DownloadImageTask(imageView).execute(imageUrl);
    }
}