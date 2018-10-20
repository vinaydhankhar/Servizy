package kvnb.hostelservicemanagement;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstFragment=new ArrayList<>();
    private final List<String> lstTiles=new ArrayList<>();

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lstTiles.get(position);
    }

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return lstFragment.get(i);
    }

    @Override
    public int getCount() {
        return lstTiles.size();
    }

    public void AddFragment(Fragment fragment,String title){
        lstFragment.add(fragment);
        lstTiles.add(title);
    }
}
