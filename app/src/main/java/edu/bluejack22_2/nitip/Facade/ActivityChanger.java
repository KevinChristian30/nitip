package edu.bluejack22_2.nitip.Facade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public abstract class ActivityChanger {

    private ActivityChanger(Context from, Context to) {

        Intent intent = new Intent(from, to.getClass());
        from.startActivity(intent);

    }

    public static void changeActivity(Context from, Class to) {

        Intent intent = new Intent(from, to);
        from.startActivity(intent);

    }


}
