
package com.emergentes.controlador;


import com.emergentes.Modelo.Producto;
import com.emergentes.dao.ProductoDAO;
import com.emergentes.dao.ProductoDAOimp;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Inicio", urlPatterns = {"/Inicio"})
public class Inicio extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         try {
            int id;
            Producto pro = new Producto();
            ProductoDAO dao = new ProductoDAOimp();
            String action = (request.getParameter("action") != null) ? request.getParameter("action") : "view";

            switch (action) {
                case "add":
                    request.setAttribute("producto", pro);
                    request.getRequestDispatcher("frmproducto.jsp").forward(request, response);
                    break;
                case "edit":

                    id = Integer.parseInt(request.getParameter("id"));
                    pro = dao.getById(id);
                    request.setAttribute("producto", pro);
                    request.getRequestDispatcher("frmproducto.jsp").forward(request, response);

                    break;
                case "delete":

                    id = Integer.parseInt(request.getParameter("id"));
                    dao.delete(id);
                    response.sendRedirect("Inicio");

                    break;
                case "view":

                    List<Producto> lista = dao.getAll();
                    request.setAttribute("productos", lista);
                    request.getRequestDispatcher("index.jsp").forward(request, response);

                    break;
                default:
                    break;

            }
        } catch (Exception ex) {
            System.out.println("Error : " + ex.getMessage());
        }
        
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ProductoDAO dao = new ProductoDAOimp();

        int id = Integer.parseInt(request.getParameter("id"));
        String descripcion = request.getParameter("descripcion");
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        float precio = Float.parseFloat(request.getParameter("precio"));
        String categoria = request.getParameter("categoria");
        
        Producto pro = new Producto();
        pro.setId(id);
        pro.setDescripcion(descripcion);
        pro.setCantidad(cantidad);
        pro.setPrecio(precio);
        pro.setCategoria(categoria);
        
        try {
            if (id == 0) {

                //adicionar registro
                dao.insert(pro);

            } else {
                //actualizar registro
                dao.update(pro);
            }
        } catch (Exception ex) {
            System.out.println("Error al : "+ ex.getMessage());
        }
        
        response.sendRedirect("Inicio");
        
        
    }

}
