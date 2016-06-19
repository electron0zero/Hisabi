package com.wordpress.electron0zero.hisabi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    TextView ResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHelp();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "Not Yet Implemented", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    //Takes all 4 sides as EditText and calculates and return Bigha
    public int calcBigha(EditText N,EditText S, EditText E, EditText W){

        //equalize and calculate length and width.
        float length = ( Float.parseFloat(N.getText().toString())
                + Float.parseFloat(S.getText().toString()) ) / 2;
        float width = ( Float.parseFloat(E.getText().toString())
                + Float.parseFloat(W.getText().toString()) ) / 2;

        //calculate area
        float area = length*width;

        // calculate Bigha by area/27225
        //for 165*165 = 27225 sq. feet area == 1 Bigha
        int Bigha  =  ((int)area / 27225);

        return Bigha;
    }

    //Takes all 4 sides as EditText and calculates and return Biswa
    public float calcBiswa(EditText N,EditText S, EditText E, EditText W){
        //Equalize and calculate length and width.

        float length = ( Float.parseFloat(N.getText().toString())
                + Float.parseFloat(S.getText().toString()) ) / 2;
        float width = ( Float.parseFloat(E.getText().toString())
                + Float.parseFloat(W.getText().toString()) ) / 2;

        //calculate area
        float area = length*width;

        //calculates biswa
        //1 Bigha = 20 biswa.
        //tmp contains Area that is not mod of bigha
        // tmp/(27225/20) == Remaining Biswa
        int tmp = (int)area%27225;
        float Biswa = tmp/(27225/20);

        return Biswa;
    }

    public void GetData(){
        //all the Edit Text Fields
        EditText mN = (EditText) findViewById(R.id.editTextN);
        EditText mS = (EditText) findViewById(R.id.editTextS);
        EditText mE = (EditText) findViewById(R.id.editTextE);
        EditText mW = (EditText) findViewById(R.id.editTextW);

       //assuming something is null, some EditText is Not Filled.
        boolean isAnyNull = true ;

        if (isEmpty(mN)){
            Toast.makeText(MainActivity.this, R.string.ToastNorth, Toast.LENGTH_SHORT).show();
        }else if(isEmpty(mS)){
            Toast.makeText(MainActivity.this, R.string.ToastSouth, Toast.LENGTH_SHORT).show();
        }else if (isEmpty(mE)){
            Toast.makeText(MainActivity.this, R.string.ToastEast, Toast.LENGTH_SHORT).show();
        }else if (isEmpty(mW)){
            Toast.makeText(MainActivity.this, R.string.ToastWest, Toast.LENGTH_SHORT).show();
        }else {
            //tested everything, now we can say nothing is null
            isAnyNull = false;
        }

        // Nothing is Empty aka isAnyNull is False
        //so proceed use (NOT)isAnyNull to proceed
        if ( !isAnyNull ){
            //calculate Bigha and Biswa
            int Bigha = calcBigha(mN,mS,mE,mW);
            float Biswa = calcBiswa(mN,mS,mE,mW);

            ResultView = (TextView)findViewById(R.id.textResult);
            String output = String.valueOf(Bigha) + " Bigha\n"
                    + String.valueOf(Biswa) + " Biswa\n";

            ResultView.setText(output);
        }
    }

    //helper function to check if any EditText is Empty
    private boolean isEmpty(EditText etText) {
        //if EditText Length after removing all whitespace is > 0
        //then it's not empty else it is
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else{
            return true;}
    }

    //on click handler
    @Override
    public void onClick(View v) {
        button = (Button)findViewById(R.id.buttonCalculate);
        if (button != null) {
            GetData();
        }
    }

    //Launch help
    public void launchHelp() {
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }

}
