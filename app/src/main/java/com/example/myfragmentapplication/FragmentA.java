package com.example.myfragmentapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentA extends Fragment {
    private FragmentAListener listener;
    private EditText editText;
    private Button buttonOk;
    private RecyclerView recyclerView;
    ArrayList<CountryDetails> listItem_country;

    MyRecyclerViewAdapter myRecyclerViewAdapter;

    public interface FragmentAListener {
        void onInputASent(CharSequence input);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_a, container, false);

        editText = v.findViewById(R.id.edit_text);
        buttonOk = v.findViewById(R.id.button_ok);
        recyclerView = v.findViewById(R.id.list_item);
//        listItem_country.add("usa");
//        listItem_country.add("India");



        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input = editText.getText();
                listener.onInputASent(input);
            }
        });

//        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, R.array.Country);
//        listView.setAdapter(arrayAdapter);
        return v;
    }

    public void updateEditText(CharSequence newText) {
        editText.setText(newText);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentAListener) {
            listener = (FragmentAListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] countryNames = getResources().getStringArray(R.array.Country);
        String[] countryCapitals = getResources().getStringArray(R.array.Country_capital);
        int[] countryImages = {R.drawable.india_flag_medium, R.drawable.united_states_of_america_flag_medium, R.drawable.japan_flag_medium,R.drawable.sweden_flag_medium, R.drawable.france_flag_medium};
        listItem_country = new ArrayList<>();
        CountryDetails myCountryDetails;
        for (int i = 0; i<countryNames.length ; i++){
            myCountryDetails = new CountryDetails();
            myCountryDetails.setCountryName(countryNames[i]);
            myCountryDetails.setCountryCapital(countryCapitals[i]);
            myCountryDetails.setImages(countryImages[i]);
            listItem_country.add(myCountryDetails);
        }

// For list view 
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.Country,
//                android.R.layout.simple_list_item_1);
//        setListAdapter(adapter);
//        listView.setAdapter(adapter); // For list view

        //Recyclerview
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(listItem_country);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleItemDivider(getResources()));
        recyclerView.setAdapter(myRecyclerViewAdapter);
//        getListView().setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

        ArrayList<CountryDetails> myCountryDetails;

        public MyRecyclerViewAdapter(ArrayList<CountryDetails> countryDetails){
            myCountryDetails = countryDetails;
        }


        @NonNull
        @Override
        public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.mylist,parent,false);
            return new MyRecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position) {
            CountryDetails countryDetails = myCountryDetails.get(position);
            holder.bindData(countryDetails);
        }

        @Override
        public int getItemCount() {
            return  myCountryDetails.size();
        }
    }

    private class MyRecyclerViewHolder extends RecyclerView.ViewHolder{
        private CountryDetails mCountryDetails;
        public ImageView mImageView;
        public TextView mNameTextView;
        public TextView mCapitalTextView;

        public MyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mNameTextView = (TextView) itemView.findViewById(R.id.title);
            mCapitalTextView = (TextView) itemView.findViewById(R.id.sub_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getActivity(),
//                            mCountryDetails.getCountryName() + " clicked!", Toast.LENGTH_SHORT)
//                            .show();

                    listener.onInputASent(mCountryDetails.getCountryName() +" " +mCountryDetails.getCountryCapital() );
                }
            });
        }


        public void bindData(CountryDetails countryDetails){
            mCountryDetails = countryDetails;
            mImageView.setImageResource(mCountryDetails.getImages());
            mNameTextView.setText(mCountryDetails.getCountryName());
            mCapitalTextView.setText(mCountryDetails.getCountryCapital());
        }



    }
}
