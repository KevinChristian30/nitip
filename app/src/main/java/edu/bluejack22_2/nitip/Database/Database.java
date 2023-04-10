package edu.bluejack22_2.nitip.Database;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {

    private static DatabaseReference db = null;
    private static final String DATABASEURL = "https://nitip-6f389-default-rtdb.firebaseio.com";

    public static DatabaseReference getInstance() {
        if (db == null) {
            db = FirebaseDatabase
                    .getInstance()
                    .getReferenceFromUrl(DATABASEURL);
        }

        return db;
    }

    private Database() {}
}
