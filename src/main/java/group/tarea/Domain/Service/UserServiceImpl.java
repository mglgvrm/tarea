package group.tarea.Domain.Service;

import group.tarea.Controller.models.AuthResponse;

public interface UserServiceImpl {
    boolean findByNameAndLastName(String name, String lastName);
}
