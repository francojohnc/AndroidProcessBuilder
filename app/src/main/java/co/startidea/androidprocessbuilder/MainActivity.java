package co.startidea.androidprocessbuilder;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    EditText cmdBox;
    Button btnRun;
    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cmdBox = (EditText)findViewById(R.id.cmdbox);
        btnRun = (Button)findViewById(R.id.run);
        textResult = (TextView)findViewById(R.id.result);
        textResult.setTypeface(Typeface.MONOSPACE);

        btnRun.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Split String from EditText to String Array
                String[] cmd = cmdBox.getText().toString().split("\\s+");
                try {
                    String cmdResult = runLinuxCmd(cmd);
                    textResult.setTextColor(Color.WHITE);
                    textResult.setText(cmdResult);
                } catch (IOException e) {
                    e.printStackTrace();
                    textResult.setTextColor(Color.RED);
                    textResult.setText("Something Wrong!\n"
                            + e.getMessage());
                }
            }});
    }

    //Run a Linux command and return result
    private String runLinuxCmd(String[] command) throws IOException {

        StringBuilder cmdReturn = new StringBuilder();

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        int c;

        while ((c = inputStream.read()) != -1) {
            cmdReturn.append((char) c);
        }

        return cmdReturn.toString();
    }
}
