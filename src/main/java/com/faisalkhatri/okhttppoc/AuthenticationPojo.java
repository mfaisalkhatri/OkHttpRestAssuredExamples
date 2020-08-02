package com.faisalkhatri.okhttppoc;

import lombok.Data;

/**
 * @author Faisal Khatri
 * @since Aug 2, 2020
 *
 */
@Data
public class AuthenticationPojo {

    private String email;
    private String password;
    
    /**
     *@author Faisal Khatri 
     *@param email
     *@param password
     */
    public AuthenticationPojo (String email, String password) {
        this.email = email;
        this.password = password;
    }
}


