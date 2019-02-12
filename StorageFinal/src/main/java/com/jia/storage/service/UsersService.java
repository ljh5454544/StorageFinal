package com.jia.storage.service;


import com.jia.storage.entity.Users;
import com.jia.storage.util.PageBean;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UsersService {

    Users login(Users users) throws NoSuchAlgorithmException;

    boolean registUser(Users users) throws NoSuchAlgorithmException;

    boolean removeUser(Users users);

    boolean updateUser(Users users) throws NoSuchAlgorithmException;

    boolean removeUsers(List<Long> array) throws ArrayIndexOutOfBoundsException;

    List<Users> getByPage(PageBean pageBean);

    Users getUsers(Users users);
}
