package com.github.ratel.utils;

import com.github.ratel.entity.User;
import com.github.ratel.exceptions.ConfirmPasswordException;
import com.github.ratel.exceptions.EntityAlreadyExistException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CheckUtil {

    public boolean checkUserByEmail(User user) {
        if (user == null) {
            return true;
        } else {
            throw new EntityAlreadyExistException("Пользователь уже существует");
        }
    }

    public boolean checkPassAndConfirmPass(String pass, String confirmPass) {
        if (pass.equals(confirmPass)) {
            return true;
        } else {
            throw new ConfirmPasswordException("Пароли не совпадают");
        }
    }

}
