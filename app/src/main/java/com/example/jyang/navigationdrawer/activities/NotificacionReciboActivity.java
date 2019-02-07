package com.example.jyang.navigationdrawer.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.jyang.navigationdrawer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificacionReciboActivity extends AppCompatActivity {


    @BindView(R.id.textoMedio)
    TextView textoMedio;
    @BindView(R.id.textViewDebajo)
    TextView textViewDebajo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion_recibo);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notificacion");
        setIntentValues();
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = getWindow();
            //w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void setIntentValues() {
        if (getIntent() != null) {
            textoMedio.setText(getIntent().getStringExtra("titulo"));
            textViewDebajo.setText(getIntent().getStringExtra("mensaje"));
        }
    }
}
