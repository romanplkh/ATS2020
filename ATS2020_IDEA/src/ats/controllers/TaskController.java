package ats.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Olena Stepanova
 */

@WebServlet(name = "TaskController")
public class TaskController extends CommonController {

    private static final String TASK_MAINT_VIEW = "/task.jsp";
    private static final String TASKS_VIEW = "/tasks.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Get URI attributes
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            //Get a list of tasks

            //Attach to request


            //Render view
            super.setView(request, TASKS_VIEW);


        } else {
            String[] pathParts = pathInfo.split("/");

            //Check for querystring param
            int taskId = super.getInteger(pathParts[1]);

            if (taskId != 0) {
                // task/:id page

                //get necessary task from DB

                //if null --> set Error message

                //else ---> return task to the view

            } else {
                // task/create


            }

            super.setView(request, TASK_MAINT_VIEW);
        }

        super.getView().forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Populates task object with data from the POST request
     * @param request POST request object
     * @param task task object to populate
     */
   /* private void populateTaskProperties(HttpServletRequest request, Task task){
        String name = super.getValue(request, "taskName");
        String description = super.getValue(request, "taskDescription");
        int duration = super.getInteger(request, "taskDuration");

        task.setName(name);
        task.setDescription(description);
        task.setDuration(duration);
        task.setCreatedAt(LocalDateTime.now());

    }
*/

}
