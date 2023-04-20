package edu.bluejack22_2.nitip.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.bluejack22_2.nitip.Fragment.DashboardFragment;
import edu.bluejack22_2.nitip.Fragment.GroupFragment;
import edu.bluejack22_2.nitip.Fragment.HistoryFragment;
import edu.bluejack22_2.nitip.Fragment.ProfileFragment;

public class BottomNavigationAdapter extends FragmentStateAdapter {
    public BottomNavigationAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DashboardFragment();
            case 1:
                return new GroupFragment();
            case 2:
                return new HistoryFragment();
            case 3:
                return new ProfileFragment();
            default:
                return new DashboardFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
