package group.tarea.Domain.Service;

import group.tarea.Controller.models.AuthResponse;
import group.tarea.Controller.models.AuthenticationRequest;
import group.tarea.Controller.models.RegisterRequest;
import group.tarea.Persistence.Entity.User;
import org.springframework.security.core.Authentication;

public interface AuthService {
    AuthResponse register (RegisterRequest Request );
    AuthResponse authenticate (AuthenticationRequest Request );
}
