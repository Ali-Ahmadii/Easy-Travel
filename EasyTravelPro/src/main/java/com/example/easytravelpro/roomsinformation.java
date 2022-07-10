package com.example.easytravelpro;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class roomsinformation {
    String target;
    roomsinformation(String target){
        this.target = target;
    }
    public void export() {
        String jdbcURL = "jdbc:mysql://localhost:3306/easy_travel";
        String username = "root";
        String password = "integral4560sini";

        String excelFilePath = "Roomsinfoof"+target+".xlsx";
 //
        try {
            Connection connectme = DriverManager.getConnection(jdbcURL, username, password);
            String sql = "SELECT * FROM rooms WHERE AuthorHotel = "+"'"+target+"'";
            PreparedStatement getinfos = connectme.prepareStatement(sql);
            ResultSet result = getinfos.executeQuery();
 
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("MySheet");
 
            writeHeaderLine(sheet);
 
            writeDataLines(result, workbook, sheet);
 
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
 
            connectme.close();
 
        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }
    private void writeHeaderLine(XSSFSheet sheet) {
 
        XSSFRow headerRow = sheet.createRow(0);
 
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Price");
 
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Rate");
 
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Comments");
 
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Facilities");
 
        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("Capacity");

        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("Photo Adress");

        headerCell = headerRow.createCell(6);
        headerCell.setCellValue("RoomID");

    }
    private void writeDataLines(ResultSet result, XSSFWorkbook workbook,XSSFSheet sheet) throws SQLException {
        int rowCount = 1;
 
        while (result.next()) {
            // System.out.println("HEY");
            String price = result.getString("Price");
            String rate = result.getString("Rate");
            String comments = result.getString("Comments");
            String facility = result.getString("Facilities");
            String capacity = result.getString("Capacity");
            String picadress = result.getString("PhotoAdress");
            String roomid = result.getString("RoomID");
            XSSFRow row = sheet.createRow(rowCount++);
 
            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(price);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(rate);

            cell = row.createCell(columnCount++);
            cell.setCellValue(comments);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(facility);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(capacity);

            cell = row.createCell(columnCount++);
            cell.setCellValue(picadress);

            cell = row.createCell(columnCount);
            cell.setCellValue(roomid);
        }
    }
 
}
