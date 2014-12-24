package com.kpi.kovalenkodima.farmerhelper.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kpi.kovalenkodima.farmerhelper.R;

/**
 * Created by kovalenkodima on 12/8/14.
 */
public class AnalysisFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_analysis,container,false);
    }
}
