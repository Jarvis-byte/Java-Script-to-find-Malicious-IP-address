package com.company;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

import static com.company.ArrayStoreClass.Arraymethod;

public class Send_HTTP_Request2 {

    static ArrayList<String> country_list = new ArrayList<>();
    static ArrayList<String> as_owner_list = new ArrayList<>();
    static ArrayList<Integer> Malicious = new ArrayList<>();
    static ArrayList<Integer> Harmless = new ArrayList<>();


    public void call() throws IOException {
        String[] ipAddress = Arraymethod();

        for (int i = 0; i < ipAddress.length; i++) {
            try {

                Send_HTTP_Request2.call_me(ipAddress[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        exportToExcel();

    }

    private static void exportToExcel() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Virus Total Details");

        for (int i = 0; i < as_owner_list.size(); i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < 4; j++) {
                Cell cell = row.createCell(j);
                if (j == 0) {
                    cell.setCellValue(Harmless.get(i));
                } else if (j == 1) {
                    cell.setCellValue(Malicious.get(i));
                } else if (j == 2) {
                    cell.setCellValue(country_list.get(i));
                } else {
                    cell.setCellValue(as_owner_list.get(i));
                }
            }


        }

        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("VirusTotalDetails.xlsx"));

            workbook.write(out);
            out.close();
            System.out.println("VirusTotalDetails.xlsx has been created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }


    private static void call_me(String Ipaddress) throws IOException {

        String url = "https://www.virustotal.com/api/v3/ip_addresses/" + Ipaddress;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("x-apikey", "abde006802b8e83f207e33b9cecff78771d43650ca3d2c498e499878f737fa17");

        int responseCode = con.getResponseCode();
        //  System.out.println("\nSending 'GET' request to URL : " + url);
        //System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        //  System.out.println(response.toString());
        //Read JSON response and print

        try {
            JSONObject myResponse = new JSONObject(response.toString());
            String country = myResponse.getJSONObject("data").getJSONObject("attributes").getString("country");

            String as_owner = myResponse.getJSONObject("data").getJSONObject("attributes").getString("as_owner");
            int harmless = myResponse.getJSONObject("data").getJSONObject("attributes").getJSONObject("last_analysis_stats").getInt("harmless");
            int malicious = myResponse.getJSONObject("data").getJSONObject("attributes").getJSONObject("last_analysis_stats").getInt("malicious");
            Harmless.add(harmless);
            Malicious.add(malicious);
            country_list.add(country);
            as_owner_list.add(as_owner);
        } catch (JSONException e) {
            System.out.println(Ipaddress + "\tMessage:-\t" + e.getMessage());

        }


    }

}

