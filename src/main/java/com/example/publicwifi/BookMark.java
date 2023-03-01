package com.example.publicwifi;

import com.example.publicwifi.db.dao.BookmarkTb;
import com.example.publicwifi.db.repository.BookmarkRepository;
import com.example.publicwifi.dto.BookmarkResponseDto;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "bookmark", value = "/bookmark")
public class BookMark  extends HttpServlet {
    private final BookmarkRepository bookmarkRepository = new BookmarkRepository();
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        Gson gson = new Gson();

        try{
            List<BookmarkResponseDto> list = bookmarkRepository.findJoin();
            response.getWriter().append(gson.toJson(list));
        } catch (Exception e){
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        String managedNumber = request.getParameter("MGR_NO");
        String bookmarkGroupName = request.getParameter("bookmarkGroupName");
        try{
            BookmarkTb bookmarkTb = new BookmarkTb();
            bookmarkTb.setBookmarkGroupName(bookmarkGroupName);
            bookmarkTb.setCrateTime(LocalDateTime.now().toString());
            bookmarkTb.setWifiInfoTbManagedNumber(managedNumber);

            bookmarkRepository.writeBookmark(bookmarkTb);
        } catch (Exception e){
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        int key = Integer.parseInt(request.getParameter("id"));

        try{
            bookmarkRepository.deleteBookmark(key);
        } catch (Exception e){
            response.setStatus(500);
            e.printStackTrace();
        }
    }
}
