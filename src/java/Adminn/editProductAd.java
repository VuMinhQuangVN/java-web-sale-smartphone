/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Adminn;

import Models.BienTheDienThoai;
import Models.BienTheDienThoaiDAO;
import Models.DienThoai;
import Models.DienThoaiDAO;
import Models.Loai;
import Models.LoaiDAO;
import Models.NguoiDung;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author ACER
 */
@WebServlet(name = "editProductAd", urlPatterns = {"/editProductAd"})
@MultipartConfig
public class editProductAd extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet editProductAd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet editProductAd at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF8");
        
        HttpSession session = request.getSession();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        // 2. Nếu chưa có thì tạo người dùng rỗng
        if (nd == null) {
            request.getRequestDispatcher("/User/login.jsp").forward(request, response);
            return;
        }

        // 3. Gửi về JSP
        request.setAttribute("nguoidung", nd);
        int maDT = Integer.parseInt(request.getParameter("id"));
        DienThoai dt = new DienThoaiDAO().getById(maDT);

        request.setAttribute("dienthoai", dt);

        LoaiDAO loaiDAO = new LoaiDAO();
        List<Loai> list = loaiDAO.getAll();

        BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();
        List<BienTheDienThoai> listt = btdao.getByDienThoai(maDT);
        
        request.setAttribute("listLoai", list);
        request.setAttribute("listBienThe", listt);
        
        String msg = (String) session.getAttribute("message");
        String type = (String) session.getAttribute("type");
        
        if (msg != null) {
            // 1. Đẩy vào request để JSP hiển thị được
            request.setAttribute("message", msg);
            request.setAttribute("type", type);
            
            // 2. Xóa ngay khỏi session để F5 không hiện lại
            session.removeAttribute("message");
            session.removeAttribute("type");
        }
        
        request.getRequestDispatcher("/Admin/editProduct.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        NguoiDung admin = (NguoiDung) session.getAttribute("user");

        // 1️⃣ Kiểm tra đăng nhập và quyền admin
        if (admin == null || !"admin".equals(admin.getQuyen())) {
            response.sendRedirect(request.getContextPath() + "/User/login.jsp");
            return;
        }
        
        int maDT = Integer.parseInt(request.getParameter("maDT"));  
        DienThoai df = new DienThoaiDAO().getById(maDT);
        
        String tenDT = request.getParameter("tenDT");
        int maLoai = Integer.parseInt(request.getParameter("maLoai"));
        String hangSx = request.getParameter("hangSx");
        String manHinh = request.getParameter("manHinh");
        String pin = request.getParameter("pin");
        String chip = request.getParameter("Chip");
        String mota = request.getParameter("mota");
        String camera = request.getParameter("camera");
         
        
        // 2. Lưu file ảnh
        Part filePart = request.getPart("anh");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        if (fileName == null || fileName.isEmpty()) {
            fileName = df.getAnh().substring(df.getAnh().lastIndexOf("/") + 1);
        } else {
            // đường dẫn thư mục gốc để lưu ảnh
            String uploadPath = getServletContext().getRealPath("/ImageProduct");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            // Lưu file lên server
            filePart.write(uploadPath + File.separator + fileName);
        }
        DienThoai d = new DienThoai();
        d.setMaDT(maDT);
        d.setTenDT(tenDT);
        d.setMaLoai(maLoai);
        d.setHangSx(hangSx);
        d.setManHinh(manHinh);
        d.setPin(pin);
        d.setChip(chip);
        d.setMota(mota);
        d.setCamera(camera);
        d.setAnh("ImageProduct/" + fileName);  // 🌟 lưu đường dẫn tương đối vào DB

        DienThoaiDAO dao = new DienThoaiDAO();
        try {
            dao.update(d);
            session.setAttribute("message", "Đã lưu những thay đổi");
            session.setAttribute("type", "success");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Lỗi hệ thống.");
            session.setAttribute("type", "error");
        }
        
        response.sendRedirect(request.getContextPath() + "/managerProductAd");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
