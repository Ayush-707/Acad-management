package org.office;

import org.misc.Connect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UploadFacultyList {

    public void listUpload(){
        String sql = "INSERT INTO faculty (email,name) VALUES (?,?) ON DUPLICATE KEY UPDATE email = ?,name=?";
        String filepath = "E:\\BTECH 3RD YEAR\\2nd Sem\\Software Engineering\\Mini Project\\Project\\ShowDown\\src\\main\\resources\\faculty.csv";
        int batchSize = 20;
        Connect conn = new Connect();
        try {

            PreparedStatement stmt = conn.Database().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            BufferedReader lineReader = new BufferedReader(new FileReader(filepath));

            String lineText = null;
            int count = 0;

            lineReader.readLine();

            while((lineText=lineReader.readLine())!=null) {

                String[] data = lineText.split(",");
                String email = data[0];
                String name = data[1];

                stmt.setString(1,email);
                stmt.setString(2,name);
                stmt.setString(3,email);
                stmt.setString(4,name);

                stmt.addBatch();

//                if(count%batchSize == 0){
//                    stmt.executeBatch();
//                }
            }
            lineReader.close();
            stmt.executeBatch();
            System.out.println("Faculty Table has been updated");






        } catch (Exception e) {
            e.printStackTrace();
        }

        OfficeOptions var4 = new OfficeOptions();
        var4.optionsOffice();



    }
}
