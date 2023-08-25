package com.assignment.spoon.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class UserInfo {
   private Long id;
   private String email;

   @Builder
   public UserInfo(Long id, String email) {
      this.id = id;
      this.email = email;
   }
}
