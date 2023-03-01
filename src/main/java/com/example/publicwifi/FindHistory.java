package com.example.publicwifi;

import com.example.publicwifi.db.dao.HistoryTb;
import com.example.publicwifi.db.repository.HistoryRepository;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "find-history", value = "/find-history")
public class FindHistory extends HttpServlet {

    private final HistoryRepository historyRepository = new HistoryRepository();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        Gson gson = new Gson();
        try {
            List<HistoryTb> list =(List<HistoryTb>) historyRepository.find(HistoryTb.class);

            response.getWriter().append(gson.toJson(list));
        } catch (Exception e){
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response)throws IOException{
        int key = Integer.parseInt(request.getParameter("id"));
        historyRepository.deleteHistory(key);
        doGet(request, response);
    }
}
