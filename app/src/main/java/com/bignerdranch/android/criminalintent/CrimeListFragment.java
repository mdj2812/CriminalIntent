package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by MA on 06/10/2017.
 */

public class CrimeListFragment extends Fragment {

    private static final int CRIME_HOLDER_VIEW_TYPE = 0;
    private static final int REQUIRES_POLICE_CRIME_HOLDER_VIEW_TYPE = 1;

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private abstract class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected Crime mCrime;

        protected TextView mTitleTextView;
        protected TextView mDateTextView;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
        }

        public abstract void bind(Crime crime);

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    private class NotRequiresPoliceCrimeHolder extends CrimeHolder {

        public NotRequiresPoliceCrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }
    }

    private class RequiresPoliceCrimeHolder extends CrimeHolder {

        private TextView mRequiresPoliceTextView;

        public RequiresPoliceCrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_requires_police_crime, parent, false));

            mRequiresPoliceTextView = (TextView) itemView.findViewById(R.id.crime_requires_police);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mRequiresPoliceTextView.setText(mCrime.isRequiresPolice() ? "Requires Police" : "Not Requires Police");
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            switch (viewType) {
                case CRIME_HOLDER_VIEW_TYPE:
                    return new NotRequiresPoliceCrimeHolder(layoutInflater, parent);
                case REQUIRES_POLICE_CRIME_HOLDER_VIEW_TYPE:
                    return new RequiresPoliceCrimeHolder(layoutInflater, parent);
                default:
                    return new NotRequiresPoliceCrimeHolder(layoutInflater, parent);
            }

        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mCrimes.get(position).isRequiresPolice() ? REQUIRES_POLICE_CRIME_HOLDER_VIEW_TYPE : CRIME_HOLDER_VIEW_TYPE;
        }
    }
}
