package com.example.networktest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientInteraction extends Thread {
    InputStream stream;
    String result;

    ClientInteraction (InputStream stream){
        this.stream = stream;
    }

    /**
     * @param studentId
     * Methode for the Interaction between Client and Server and showing the
     * output of the Server on the designated spot on the application
     * @throws Exception
     * throws Exception when the Server is not available
     */
    public String ServerClientInteraction(InputStream studentId) throws Exception {
        String sentence;
        String modifiedSentence;

        BufferedReader inputUser = new BufferedReader(new InputStreamReader(studentId));

        Socket client = new Socket("se2-isys.aau.at", 53212);

        DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

        sentence = inputUser.readLine();

        outToServer.writeBytes(sentence + '\n');

        modifiedSentence = inFromServer.readLine();
        client.close();
        return modifiedSentence;
    }

    /**
     * executing the ServerClientInteraction in a thread
     */
    @Override
    public void run() {
        try {
            result = ServerClientInteraction(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return String
     * to get the Result after the Thread is finished
     */
    public String getResult() {
        return result;
    }
}
