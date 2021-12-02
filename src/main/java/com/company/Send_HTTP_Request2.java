package com.company;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.json.JSONException;
import org.json.JSONObject;

import static com.company.ArrayStoreClass.Arraymethod;

public class Send_HTTP_Request2 {
    static Map data = new HashMap();
    static ArrayList<String> country_list = new ArrayList<>();
    static ArrayList<String> as_owner_list = new ArrayList<>();

    public void call() throws IOException {
        String[] ipAddress = Arraymethod();

        for (int i = 0; i < ipAddress.length; i++) {
            try {

                Send_HTTP_Request2.call_me(ipAddress[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println(country_list);
        System.out.println(as_owner_list);
        //  exportToExcel();

    }

//    private void exportToExcel() throws IOException {
////        XSSFWorkbook workbook = new XSSFWorkbook();
////        XSSFSheet sheet = workbook.createSheet("IP Address Check");
//        Iterator hmIterator = data.entrySet().iterator();
//        int rowno = 0;
//
//        while (hmIterator.hasNext()) {
//
//            Map.Entry mapElement = (Map.Entry) hmIterator.next();
//
//            HSSFRow row = hssfSheet.createRow(rowno++);
//
//            row.createCell(0).setCellValue((String) mapElement.getKey());
//            row.createCell(1).setCellValue((String) mapElement.getValue());
//
//        }
//        FileOutputStream fos = new FileOutputStream(".\\datafiles\\IpTracker.xlsx");
//        hssfWorkbook.write(fos);
//        fos.close();
//
//    }

    private static void call_me(String Ipaddress) throws IOException, JSONException {

        String url = "https://www.virustotal.com/api/v3/ip_addresses/" + Ipaddress;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("x-apikey", "37885d07c33824bf400eb283ad1280625716abb1074f32b8a6135a93caa412c7");
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
        JSONObject myResponse = new JSONObject(response.toString());

        String country = myResponse.getJSONObject("data").getJSONObject("attributes").getString("country");

        String as_owner = myResponse.getJSONObject("data").getJSONObject("attributes").getString("as_owner");

        country_list.add(country);
        as_owner_list.add(as_owner);
    }

}

