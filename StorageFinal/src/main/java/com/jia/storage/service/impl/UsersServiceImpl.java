package com.jia.storage.service.impl;

import com.jia.storage.entity.Users;
import com.jia.storage.mapper.UsersMapper;
import com.jia.storage.service.UsersService;
import com.jia.storage.util.MD5Utils;
import com.jia.storage.util.PageBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Users login(Users users) throws NoSuchAlgorithmException {
        if (users == null)
            return users;
        String username = users.getUsername();
        String password = users.getPassword();
        password = MD5Utils.encrypt(password);

        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);

        System.out.println(usersMapper);

        List<Users> list = usersMapper.selectByMap(map);

        System.out.println(list);

        if (list.size() != 1){
            return users;
        }
        return list.get(0);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean registUser(Users users) throws NoSuchAlgorithmException {
        if (users == null)
            return false;
        String encryptPassword = MD5Utils.encrypt(users.getPassword());
        users.setPassword(encryptPassword);
        int i = usersMapper.insertSelective(users);
        rabbitTemplate.convertAndSend("storage.direct","regist", users);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeUser(Users users) {
        if (users == null || users.getId() == null)
            return false;
        Users users1 = usersMapper.selectByPrimaryKey(users.getId());
        int i = usersMapper.deleteByPrimaryKey(users.getId());
        rabbitTemplate.convertAndSend("storage.direct","remove", users1);
        return i > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean updateUser(Users users) throws NoSuchAlgorithmException {
        if (users == null || users.getId() == null)
            return false;
        String password = users.getPassword();
        users.setPassword(MD5Utils.encrypt(password));
        int i = usersMapper.updateByPrimaryKeySelective(users);
        return i > 0;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean removeUsers(List<Long> array) throws ArrayIndexOutOfBoundsException {
        if (array == null || array.size() < 1)
            return false;
        int count = 0;
        for (Long u : array){
            Users users1 = usersMapper.selectByPrimaryKey(u);
            rabbitTemplate.convertAndSend("storage.direct","remove", users1);
            count += usersMapper.deleteByPrimaryKey(u);
            Users users = new Users();
            users.setId(u);
        }
        if (count != array.size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        return count == array.size();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Users> getByPage(PageBean pageBean) {
        Map map = new HashMap();
        map.put("startRow", (pageBean.getPageNum() - 1) * pageBean.getRowsPerPage());
        map.put("rowsPerPage", pageBean.getRowsPerPage());
        pageBean.setRowsNum(usersMapper.selectRowsNum());
        pageBean.setMaxPage( (pageBean.getRowsNum()+pageBean.getRowsPerPage()-1) / pageBean.getRowsPerPage() );
        return usersMapper.selectByMap(map);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Users getUsers(Users users) {
        if (users == null || users.getId() == null)
            return null;
        return usersMapper.selectByPrimaryKey(users.getId());
    }
}
