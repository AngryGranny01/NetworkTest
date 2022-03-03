package com.example.networktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private TextView outPutServer;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.buttonSend);

        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView studentId = findViewById(R.id.editTextStudentId);
                        InputStream stream = new ByteArrayInputStream(studentId.getText().toString().getBytes());
                        try {
                            ServerClientInteraction(stream);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    public void ServerClientInteraction(InputStream studentId) throws Exception {
        String sentence;
        String modifiedSentence;

        BufferedReader inputUser = new BufferedReader(new InputStreamReader(studentId));

        Socket client = new Socket("se2-isys.aau.at", 53212);

        DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

        sentence = inputUser.readLine();

        outToServer.writeBytes(sentence + '\n');

        modifiedSentence = inFromServer.readLine();
        outPutServer = findViewById(R.id.textViewAnswerOfServer);
        outPutServer.setText(modifiedSentence);
        client.close();
    }

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
        String result;
        result = sortAscendingOrder(evenNumbers.toString()) + sortAscendingOrder(oddNumbers.toString());
        return result;
    }

    public String sortAscendingOrder(String sort){
        char charArray[] = sort.toCharArray();
        Arrays.sort(charArray);
        return Arrays.toString(charArray);
    }
}