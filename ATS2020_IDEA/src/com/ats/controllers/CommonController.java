package com.ats.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class CommonController extends HttpServlet {
    private RequestDispatcher view;


    public RequestDispatcher getView() {
        return view;
    }

    //RENDER VIEW WE WANT TO REDIRECT TO
    public void setView(HttpServletRequest request, String viewPath) {
        view = request.getRequestDispatcher(viewPath);
    }

    protected int getInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    //GET INTEGER FOR POST PARAM
    protected int getInteger(HttpServletRequest request, String key) {
        try {
            return Integer.parseInt(request.getParameter(key));
        } catch (Exception e) {
            return 0;
        }
    }

    protected double getDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }

    //GET INTEGER FOR POST PARAM
    protected double getDouble(HttpServletRequest request, String key) {
        try {
            return Double.parseDouble(request.getParameter(key));
        } catch (Exception e) {
            return 0.0;
        }
    }




    protected String getValue(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }
}
