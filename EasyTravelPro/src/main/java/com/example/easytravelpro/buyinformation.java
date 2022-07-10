package com.example.easytravelpro;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.sql.*;
public class buyinformation {
    String target;
    buyinformation(String target){
        this.target = target;
    }
     
    public void export() {
        String jdbcURL = "jdbc:mysql://localhost:3306/easy_travel";
        String username = "root";
        String password = "integral4560sini";
 
        String excelFilePath = target+".xlsx";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String sql = "SELECT * FROM buyhistory WHERE RoomAuthor = "+"'"+target+"'";
            Statement statement = connection.createStatement();
 
            ResultSet result = statement.executeQuery(sql);
 
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("MySheet");
 
            writeHeaderLine(sheet);
 
            writeDataLines(result, workbook, sheet);
 
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }
 
    private void writeHeaderLine(XSSFSheet sheet) {
 
        Row headerRow = sheet.createRow(0);
 
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Buyer");
 
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("RoomAuthor");
 
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("RoomID");
 
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Price");
 
        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("Nights");
    }
 
    private void writeDataLines(ResultSet result, XSSFWorkbook workbook,XSSFSheet sheet) throws SQLException {
        int rowCount = 1;
 
        while (result.next()) {
            String buyer = result.getString("Buyer");
            String roomauthor = result.getString("RoomAuthor");
            String roomid = result.getString("RoomID");
            String price = result.getString("Price");
            String nights = result.getString("Nights");
 
            Row row = sheet.createRow(rowCount++);
 
            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(buyer);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(roomauthor);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(roomid);
 
            cell = row.createCell(columnCount++);
            cell.setCellValue(price);
 
            cell = row.createCell(columnCount);
            cell.setCellValue(nights);
 
        }
    }
 
}