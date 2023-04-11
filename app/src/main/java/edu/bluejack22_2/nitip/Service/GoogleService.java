package edu.bluejack22_2.nitip.Service;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import edu.bluejack22_2.nitip.R;

public class GoogleService {
    private static GoogleSignInOptions gso = null;
    private static GoogleSignInClient gsc = null;

    public static GoogleSignInOptions getGso(Context ctx) {
        if (gso == null) {
            gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(ctx.getString(edu.bluejack22_2.nitip.R.string.default_web_client_id))
                    .requestEmail()
                    .build();
        }
        return gso;
    }

    public static GoogleSignInClient getGsc(Context ctx) {
        if (gsc == null) {
            gsc = GoogleSignIn.getClient(ctx, gso);
        }
        return gsc;
    }
}
