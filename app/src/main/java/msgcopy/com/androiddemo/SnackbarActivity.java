package msgcopy.com.androiddemo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SnackbarActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.show:
                Snackbar snackbar = Snackbar.make(view,"这是massage", Snackbar.LENGTH_LONG);
                View v = snackbar.getView();
                if (null!=v){
                    v.setBackgroundColor(getResources().getColor(R.color.limb_tap_page_idicator_text_color));
                    ((TextView) v.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.bg_activity_base));
                    ((Button) v.findViewById(R.id.snackbar_action)).setTextColor(getResources().getColor(R.color.bg_activity_base));
                }
                snackbar.setAction("这是action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SnackbarActivity.this,"你点击了action",Toast.LENGTH_SHORT).show();
                    }
                });
                snackbar.show();
                break;
        }
    }

}
