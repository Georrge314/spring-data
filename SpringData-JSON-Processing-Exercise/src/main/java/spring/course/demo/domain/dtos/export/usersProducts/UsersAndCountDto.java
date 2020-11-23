package spring.course.demo.domain.dtos.export.usersProducts;

import com.google.gson.annotations.Expose;
import spring.course.demo.domain.dtos.export.otherDto.UserExportDto;

import java.util.Set;

public class UsersAndCountDto {
    @Expose
    private int usersCount;
    @Expose
    private Set<UserDto> users;

    public UsersAndCountDto() {
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public Set<UserDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDto> users) {
        this.users = users;
    }
}
