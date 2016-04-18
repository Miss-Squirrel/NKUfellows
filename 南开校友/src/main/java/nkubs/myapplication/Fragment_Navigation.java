package nkubs.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;



public class Fragment_Navigation extends Fragment implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioGroup;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container,false);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup_navigation);
        radioGroup.setOnCheckedChangeListener(this);
        return  view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radioButton_navigation_news:
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), News.class);
                startActivity(intent1);
                break;
            case R.id.radioButton_navigation_fellows:
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), Fellows.class);
                startActivity(intent2);
                break;
            case R.id.radioButton_navigation_donation:
                Intent intent3 = new Intent();
                intent3.setClass(getActivity(), Donation.class);
                startActivity(intent3);
                break;
            case R.id.radioButton_navigation_association:
                Intent intent4 = new Intent();
                intent4.setClass(getActivity(),Association.class);
                startActivity(intent4);
                break;
            case R.id.radioButton_navigation_mine:
                Intent intent5 = new Intent();
                intent5.setClass(getActivity(), Mine.class);
                startActivity(intent5);
                break;

        }
    }
}
