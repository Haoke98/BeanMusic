package com.sadam.ui4.FragmentSelfPage.FragmentLike;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.sadam.ui4.ActivityLogin;
import com.sadam.ui4.Data.MySqLiteOpenHelper;
import com.sadam.ui4.Data.User;
import com.sadam.ui4.FragmentHomePage.FragmentHomePage;
import com.sadam.ui4.FragmentSelfPage.FragmentComposition.VideoAdapter;
import com.sadam.ui4.MainActivity;
import com.sadam.ui4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLike#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLike extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentLike() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLike.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLike newInstance(String param1, String param2) {
        FragmentLike fragment = new FragmentLike();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private MainActivity mainActivity;
    private User currUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_fragmentlike);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        VideoAdapter videoAdapter = new VideoAdapter((MainActivity) getActivity());
        recyclerView.setAdapter(videoAdapter);
        mainActivity = (MainActivity) getActivity();
        MySqLiteOpenHelper mySqLiteOpenHelper = mainActivity.getMySqLiteOpenHelper();
        currUser = ActivityLogin.getCurrentUserFromSharedPrefrences(getContext(), mySqLiteOpenHelper);
        videoAdapter.setVideos(FragmentHomePage.initVideos());
        return view;
    }
}