package group.tarea.Domain.Mapper;


import group.tarea.Domain.Dto.UsuarioDto;
import group.tarea.Persistence.Entity.User;

public class UsuarioMapper {

    public static User toEntity(UsuarioDto userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUser(userDTO.getUser());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        return user;
    }

    public static UsuarioDto toDto(User user) {
        UsuarioDto userDTO = new UsuarioDto();
        userDTO.setId(user.getId());
        userDTO.setUser(user.getUser());
        userDTO.setName(user.getName());
        userDTO.setLastName(user.getLastName());
        return userDTO;
    }
}
