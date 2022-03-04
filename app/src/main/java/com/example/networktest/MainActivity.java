package com.example.networktest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView outPutServer;
    private Button btnSend;
    private Button btnCalculate;
    EditText studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.buttonSend);
        btnCalculate = findViewById(R.id.buttonCalculate);
        studentId = (EditText) findViewById(R.id.editTextStudentId);
        outPutServer = (TextView) findViewById(R.id.textViewEditableAnswerOfServer);

        btnSend.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);

    }

    /**
     * @param studentId
     * @return student ID sorted. First even than odd numbers and in
     * ascending order
     */
    public String sortStudentIdEvenThanOdd(String studentId) {
        StringBuilder oddNumbers = new StringBuilder();
        StringBuilder evenNumbers = new StringBuilder();
        for (int i = 0; i < studentId.length(); i++) {
            if (studentId.charAt(i) % 2 == 0) {
                evenNumbers.append(studentId.charAt(i));
            } else {
                oddNumbers.append(studentId.charAt(i));
            }
        }
        String result = "";
        result += sortAscendingOrder(evenNumbers.toString()) + sortAscendingOrder(oddNumbers.toString());
        result = result.replaceAll("\\D+","");
        return result;
    }

    /**
     * @param sort
     * @return sort every input in ascending order
     */
    public String sortAscendingOrder(String sort){
        char charArray[] = sort.toCharArray();
        Arrays.sort(charArray);
        return Arrays.toString(charArray);
    }

    /**
     * @param view
     * Listener for both buttons with switch statements too decide
     * what button was clicked. Button Send is for the Server Client Interaction where
     * a Thread is used
     * ButtonCalculate its function is described in the upper two methods
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        String result = "";
        switch (view.getId()){
            case R.id.buttonSend:
                InputStream stream = new ByteArrayInputStream(studentId.getText().toString().getBytes());
                ClientInteraction clientInteraction = new ClientInteraction(stream);
                clientInteraction.start();
                try {
                    clientInteraction.join(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result = clientInteraction.getResult();
                break;
            case R.id.buttonCalculate:
                result = sortStudentIdEvenThanOdd(studentId.getText().toString());
                break;
        }
        outPutServer.setText(result);
    }
}