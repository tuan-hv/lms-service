package com.smartosc.fintech.lms.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;


@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto extends AuditDto {

  long id;

  String email;

  String password;

  Integer status;

  Timestamp lastPasswordResetDate;

  Timestamp lastLoggedInDate;

  String uuid;
}
