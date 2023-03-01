package com.example.publicwifi;

import com.example.publicwifi.db.DataReader;
import com.example.publicwifi.db.dao.BookmarkGroupTb;
import com.example.publicwifi.db.repository.BookmarkGroupRepository;
import com.example.publicwifi.db.repository.BookmarkRepository;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "bookmark-group", value = "/bookmark-group")
public class BookMarkGroup extends HttpServlet {
    private final BookmarkGroupRepository bookmarkGroupRepository = new BookmarkGroupRepository();
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        Gson gson = new Gson();

        try {
            List<BookmarkGroupTb> list = (List<BookmarkGroupTb>) bookmarkGroupRepository.find(BookmarkGroupTb.class);
            list.sort(Comparator.comparingInt(BookmarkGroupTb::getOrder));

            response.getWriter().append(gson.toJson(list));
        } catch (Exception e) {
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        String view = request.getParameter("view");

        String bookmarkName = request.getParameter("bookmark-name");
        int order = Integer.parseInt(request.getParameter("order"));
        try {
            BookmarkGroupTb bookMarkGroupTb = new BookmarkGroupTb();
            bookMarkGroupTb.setOrder(order);
            bookMarkGroupTb.setBookmarkName(bookmarkName);
            bookMarkGroupTb.setCreateTime(LocalDateTime.now().toString());

            bookmarkGroupRepository.writeBookmarkGroup(bookMarkGroupTb);
        } catch (Exception e) {
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        int key = Integer.parseInt(request.getParameter("key"));
        int nowOrder = Integer.parseInt(request.getParameter("now-order"));
        String bookmarkName = request.getParameter("bookmark-name");
        int order = Integer.parseInt(request.getParameter("after-order"));
        try {
            bookmarkGroupRepository.updateBookMarkGroupByName(key, nowOrder, bookmarkName, order);
        } catch (Exception e) {
            response.setStatus(500);
            e.printStackTrace();
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        String order = request.getParameter("id");
        BookmarkRepository bookmarkRepository = new BookmarkRepository();

        try {
            String deletedName = bookmarkGroupRepository.deleteBookmarkGroup(Integer.parseInt(order));
            bookmarkRepository.deleteBookmarkByGroupName(deletedName);
        } catch (Exception e) {
            response.setStatus(500);
            e.printStackTrace();
        }
    }
}
