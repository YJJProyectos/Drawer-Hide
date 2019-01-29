package com.example.jyang.navigationdrawer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        setIntentValues();
    }

    private void setIntentValues() {
        if (getIntent() != null) {
            textoMedio.setText(getIntent().getStringExtra("titulo"));
            textViewDebajo.setText(getIntent().getStringExtra("mensaje"));
        }
    }
}
