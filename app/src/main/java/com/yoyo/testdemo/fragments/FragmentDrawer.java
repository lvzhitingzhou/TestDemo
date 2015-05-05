package com.yoyo.testdemo.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yoyo.testdemo.R;
import com.yoyo.testdemo.activities.ActivityMain;
import com.yoyo.testdemo.adapters.DrawerRecyclerAdapter;
import com.yoyo.testdemo.application.AppApplication;
import com.yoyo.testdemo.domain.Information;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDrawer extends Fragment {
   private RecyclerView drawerList;
   private Boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    public static String SP_KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private DrawerLayout mDrawLayout;
    private View mContainer;
    private  ActionBarDrawerToggle mDrawerToggle;

    public FragmentDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = AppApplication.readFromPreferences(getActivity(), SP_KEY_USER_LEARNED_DRAWER, false);
        mFromSavedInstanceState = (savedInstanceState == null) ? false: true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawerList = (RecyclerView) view.findViewById(R.id.drawerList);
        drawerList.setAdapter( new DrawerRecyclerAdapter(getActivity(), getData()));
        drawerList.setLayoutManager(new LinearLayoutManager(getActivity()));

        drawerList.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), drawerList, new ClickListener() {
            @Override
            public void click(View view, int position) {
                Toast.makeText(getActivity(), "clicked iem", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void longClick(View view, int position) {
                Toast.makeText(getActivity(), "longClicked tiem", Toast.LENGTH_SHORT).show();

            }
        } ));
    }

    private List<Information> getData() {
        int[] iconIds = {R.drawable.ic_action_search_orange, R.drawable.ic_action_trending_orange, R.drawable.ic_action_upcoming_orange};
        String[] titles = getResources().getStringArray(R.array.drawer_tabs);
        List<Information> data = new ArrayList<Information>();
        for (int i = 0; i < titles.length; i++) {
            Information information = new Information();
            information.title = titles[i];
            information.iconId = iconIds[i];
            data.add(information);
        }
        return data;
    }

    /**
     * 关联drawerlayout，toolbar
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mDrawLayout = drawerLayout;
        mContainer = getActivity().findViewById(fragmentId);

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.drawer_open_desc, R.string.drawer_close_desc){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    AppApplication.saveToSharedPreference(getActivity(), SP_KEY_USER_LEARNED_DRAWER, true);
                }
                getActivity().supportInvalidateOptionsMenu(); // toolbar标题相应改变

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset/2);
            }
        };

        mDrawLayout.setDrawerListener(mDrawerToggle);
        // 第一次打开软件，打开侧边栏，让用户知道有侧边栏
        mDrawLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
                if(!mUserLearnedDrawer && !mFromSavedInstanceState){
                    mDrawLayout.openDrawer(mContainer);
                }
            }
        });
    }

    public interface ClickListener{
        public void click(View view, int position);
        public void longClick(View view, int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private ClickListener clickListener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(clickListener!=null && child!=null){
                        clickListener.longClick(child, recyclerView.getChildPosition(child));
                    }

                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if(clickListener!=null && child!=null && gestureDetector.onTouchEvent(e)){
                clickListener.click(child, rv.getChildPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }
    }

}
