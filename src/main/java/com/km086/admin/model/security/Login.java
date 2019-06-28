package com.km086.admin.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Login  implements Serializable {

    private String userName;

    private String password;

    private static final long serialVersionUID = -5251491590250658256L;
}
