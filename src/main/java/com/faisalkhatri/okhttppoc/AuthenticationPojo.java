/*
        Copyright (c) 2022 Mohammad Faisal Khatri

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

package com.faisalkhatri.okhttppoc;

import lombok.Data;

/**
 * @author Faisal Khatri
 * @since Aug 2, 2020
 */
@Data
public class AuthenticationPojo {

    private String email;
    private String password;

    /**
     * @param email
     * @param password
     *
     * @author Faisal Khatri
     */
    public AuthenticationPojo (String email, String password) {
        this.email = email;
        this.password = password;
    }
}