/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.genkey;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * @author hybof
 */
public class RequestProcessor {

    public static void requestProcessor(String filepath) throws IOException {
        BufferedReader br = new BufferedReader(
                new FileReader(filepath));
        String s;
        while((s = br.readLine()) != null){
            System.out.println(s);
        }
        br.close();
    }



    }
















    



