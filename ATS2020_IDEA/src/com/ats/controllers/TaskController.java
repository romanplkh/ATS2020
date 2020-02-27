package com.ats.controllers;

import com.ats.controllers.CommonController;
import com.ats.models.ErrorViewModel;
import com.ats.models.Task;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Olena Stepanova
 */

@WebServlet(name = "TaskController", urlPatterns = {"/task", "/tasks"})
public class TaskController extends CommonController {

    private static final String TASK_MAINT_VIEW = "/task.jsp";
    private static final String TASKS_VIEW = "/tasks.jsp";
    private static final String TASK_DETAILS_VIEW = "/task-details.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Get URI attributes
        String pathInfo = request.getPathInfo();

        //Object to hold entity and pass to view
        Task task = new Task();
        List<Task> taskList = new ArrayList();

        //List all tasks page
        if (pathInfo == null) {
            //Get a list of tasks ------MOCK DATA-----need to remove
            task.setId(1);
            task.setName("Router Config");
            task.setDescription("Router configuration");
            task.setDuration(30);
            task.setCreatedAt(LocalDateTime.now());
            taskList.add(task);

            //Attach to request
            request.setAttribute("taskList", taskList);

            //Render view
            super.setView(request, TASKS_VIEW);


        } else {
            String[] pathParts = pathInfo.split("/");

            //Check for querystring param
            int taskId = super.getInteger(pathParts[1]);

            //Details or Update task page /task/:id/[details/update]
            if (taskId != 0) {

                //get necessary task from DB
                //Task task = new Task(); ------MOCK DATA
                task.setId(1);
                task.setName("Router Config");
                task.setDescription("Router configuration");
                task.setDuration(30);
                task.setCreatedAt(LocalDateTime.now());
                //if null --> set Error message

                if (task == null) {
                    request.setAttribute("error", new ErrorViewModel(
                            "Requested task was not found"
                    ));
                } else {
                    String page = pathParts[2];
                    switch (page.toLowerCase()) {
                        case "details":

                            request.setAttribute("task", task);
                            super.setView(request, TASK_DETAILS_VIEW);
                            break;

                        case "update":

                            request.setAttribute("task", task);
                            super.setView(request, TASK_MAINT_VIEW);
                            break;

                    }
                }

            } else {
                //Create new task page
                request.setAttribute("task", task);
                super.setView(request, TASK_MAINT_VIEW);
            }


        }

        super.getView().forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Populates task object with data from the POST request
     *
     * @param request POST request object
     * @param task    task object to populate
     */
    private void populateTaskProperties(HttpServletRequest request, Task task) {
        String name = super.getValue(request, "taskName");
        String description = super.getValue(request, "taskDescription");
        int duration = super.getInteger(request, "taskDuration");

        task.setName(name);
        task.setDescription(description);
        task.setDuration(duration);
        task.setCreatedAt(LocalDateTime.now());

    }


}
