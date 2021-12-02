package com.company;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;

import static com.company.ArrayStoreClass.Arraymethod;

public class Main {
    //static HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
    // static HSSFSheet hssfSheet = hssfWorkbook.createSheet("Custom Sheet");
    public static void main(String[] args) throws IOException {
        // write your code here
        Send_HTTP_Request2 send = new Send_HTTP_Request2();

        send.call();


    }

    public static void ExcelSheet() {

        String path = "C:\\Users\\arkam\\Downloads\\randstad.cn_last 7days.csv";
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                System.out.println(values[6]);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

