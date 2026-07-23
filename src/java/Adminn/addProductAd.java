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
@WebServlet(name = "addProductAd", urlPatterns = {"/addProductAd"})
@MultipartConfig
public class addProductAd extends HttpServlet {

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
            out.println("<title>Servlet addProductAd</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addProductAd at " + request.getContextPath() + "</h1>");
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
        LoaiDAO loaiDAO = new LoaiDAO();
        List<Loai> list = loaiDAO.getAll();

        request.setAttribute("listLoai", list);
        request.getRequestDispatcher("/Admin/addProduct.jsp").forward(request, response);
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
        NguoiDung nd = (NguoiDung) session.getAttribute("user");

        // 2. Nếu chưa có thì tạo người dùng rỗng
        if (nd == null) {
            request.getRequestDispatcher("/User/login.jsp").forward(request, response);
            return;
        }
        String tenDT = request.getParameter("tenDT");
        int maLoai = Integer.parseInt(request.getParameter("maLoai"));
        String hangSx = request.getParameter("hangSx");
        String manHinh = request.getParameter("manHinh");
        String pin = request.getParameter("pin");
        String chip = request.getParameter("Chip");
        String mota = request.getParameter("mota");
        String camera = request.getParameter("camera");

        String mauSac = request.getParameter("mauSac");
        String ram = request.getParameter("RAM");
        String rom = request.getParameter("ROM");
        double gia = Double.parseDouble(request.getParameter("gia").trim());
        int soluong = Integer.parseInt(request.getParameter("soLuong"));
        // 2. Lưu file ảnh
        Part filePart = request.getPart("anh");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // đường dẫn thư mục gốc để lưu ảnh
        String uploadPath = getServletContext().getRealPath("/ImageProduct");
        System.out.println("UPLOAD PATH = " + uploadPath);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Lưu file lên server
        filePart.write(uploadPath + File.separator + fileName);

        DienThoai d = new DienThoai();
        d.setTenDT(tenDT);
        d.setMaLoai(maLoai);
        d.setHangSx(hangSx);
        d.setManHinh(manHinh);
        d.setPin(pin);
        d.setChip(chip);
        d.setMota(mota);
        d.setCamera(camera);
        d.setAnh("ImageProduct/" + fileName);  // 🌟 lưu đường dẫn tương đối vào DB

        try {
            DienThoaiDAO dao = new DienThoaiDAO();
            int maDT = dao.insert(d);
            
            BienTheDienThoai bt = new BienTheDienThoai();
            bt.setMaDT(maDT);
            bt.setMauSac(mauSac);
            bt.setRAM(ram);
            bt.setROM(rom);
            bt.setGia(gia);
            bt.setSoLuong(soluong);
            
            BienTheDienThoaiDAO btdao = new BienTheDienThoaiDAO();
            btdao.insert(bt);
            
            session.setAttribute("message", "Thêm điện thoại và biến thể điện thoại thành công.");
            session.setAttribute("type", "success");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Thêm điện thoại và biến thể điện thoại thất bại do lỗi hệ thống.");
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
