package com.ats.controllers;


import com.ats.business.ITaskService;
import com.ats.business.TaskServiceFactory;
import com.ats.controllers.CommonController;
import com.ats.models.ErrorViewModel;
import com.ats.models.ITask;
import com.ats.models.Task;
import com.ats.models.TaskFactory;
import com.ats.repository.ITaskRepo;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        ITask task = TaskFactory.createInstance();
        ITaskService service = TaskServiceFactory.createInstance();


        //List all tasks page
        if (pathInfo == null) {
            //Get a list of tasks
            List<ITask> taskList = TaskFactory.createListInstance();
            taskList = service.getAllTasks();

            if (taskList.size() > 0) {
                request.setAttribute("taskList", taskList);
            }

            //Render view
            super.setView(request, TASKS_VIEW);


        } else {
            String[] pathParts = pathInfo.split("/");

            //Check for querystring param
            int taskId = super.getInteger(pathParts[1]);

            //Details or Update task page /task/:id/[details/update]
            if (taskId != 0) {

                //get necessary task from DB
                task = service.getTask(taskId);

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

//                        case "update":
//
//                            request.setAttribute("task", task);
//                            super.setView(request, TASK_MAINT_VIEW);
//                            break;

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


        //set view for POST

        ITask task = TaskFactory.createInstance();
        ITaskService service = TaskServiceFactory.createInstance();

        //NEED TO FIX URL PATH after save
        try {

            String action = super.getValue(request, "action").toLowerCase();

            int taskId = super.getInteger(request, "taskId");

            switch (action) {
                case "save":
                    populateTaskProperties(request, task);

                    if (task.getErrors().isEmpty()) {
                        //create new task in DB
                        task = service.createTask(task);


                    } else {

                        request.setAttribute("validationError", task.getErrors());
                        request.setAttribute("task", task);
                        super.setView(request, TASK_MAINT_VIEW);

                    }

                    break;

                case "update":

                    break;

            }


        } catch (Exception e) {
            super.setView(request, TASK_MAINT_VIEW);
            request.setAttribute("error", new ErrorViewModel(
                    "Sorry, an error occurred. Try again later"
            ));
        }

        if (task.getErrors().size() > 0) {
            super.getView().forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/tasks");
        }

    }

    /**
     * Populates task object with data from the POST request
     *
     * @param request POST request object
     * @param task    task object to populate
     */
    private void populateTaskProperties(HttpServletRequest request, ITask task) {
        String name = super.getValue(request, "taskName");
        String description = super.getValue(request, "taskDescription");
        int duration = super.getInteger(request, "taskDuration");

        task.setName(name);
        task.setDescription(description);
        task.setDuration(duration);
        task.setCreatedAt(LocalDateTime.now());

    }


}
