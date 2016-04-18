package nkubs.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TabFragment_Invite extends Fragment {

    private Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.framgent_signupinvi,null);

        button = (Button) view.findViewById(R.id.button_SignUpInvi_Valid);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(getActivity(), "验证！", Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent();
                intent.setClass(getActivity(),SignUpFollow.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
