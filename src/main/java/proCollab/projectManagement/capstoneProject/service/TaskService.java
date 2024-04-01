package proCollab.projectManagement.capstoneProject.service;

import java.util.List;

import proCollab.projectManagement.capstoneProject.model.Project;
import proCollab.projectManagement.capstoneProject.model.Task;
import proCollab.projectManagement.capstoneProject.model.User;

public interface TaskService {

    void createTask(Task task);

    void updateTask(Long id, Task task);

    void deleteTask(Long id);

    List<Task> findAll();

    List<Task> findByOwnerOrderByDateDesc(User user);

    void setTaskCompleted(Long id);

    void setTaskNotCompleted(Long id);

    List<Task> findFreeTasks();

    Task getTaskById(Long taskId);

    void assignTaskToUser(Task task, User user);

    void unassignTask(Task task, User user);

    long countTasks();

    long countCompletedTasks();

    void deleteAllTaskFromProject(Project project);
}
