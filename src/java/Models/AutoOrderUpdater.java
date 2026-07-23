/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Timer;
import java.util.TimerTask;

public class AutoOrderUpdater {
    private static boolean started = false;

    public static synchronized void startScheduler() {
        if (started) return; 

        started = true;
        Timer timer = new Timer(true); 
        
        long oneDay = 24 * 60 * 60 * 1000L; 
        long delay = 0; // chạy ngay khi khởi động server

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try (Connection conn = new dbConnect().getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                        "UPDATE donhang " +
                        "SET trangThai='Hoàn tất', ngayHoantat=NOW() " +
                        "WHERE trangThai='Đã giao' " +
                        "AND TIMESTAMPDIFF(DAY, ngayGiao, NOW()) >= 1")) {

                    int updated = ps.executeUpdate();
                    System.out.println("✅ [AutoUpdater] Da tu đong hoan tat " + updated + " don hang.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, delay, oneDay);
    }
}


