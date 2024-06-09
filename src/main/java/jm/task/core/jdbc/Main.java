package jm.task.core.jdbc;


//Создание таблицы User(ов)
//Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
//Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
//Очистка таблицы User(ов)
//Удаление таблицы


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    private static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {

        userService.createUsersTable();
        userService.saveUser("Bob", "Petrov", (byte)51);
        userService.saveUser("Nick", "Ivanov", (byte)30);
        userService.saveUser("Maria", "Letova", (byte)25);
        userService.saveUser("Tanya", "Ivanova", (byte)32);

        List<User> userList = userService.getAllUsers();
        userList.forEach(System.out::println);

//        userService.cleanUsersTable();
//        userService.dropUsersTable();
    }
}
