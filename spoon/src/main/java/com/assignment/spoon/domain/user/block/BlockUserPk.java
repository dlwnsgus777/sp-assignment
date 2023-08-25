package com.assignment.spoon.domain.user.block;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Getter
@NoArgsConstructor
public class BlockUserPk implements Serializable {

   @Column(name = "user_id")
   private Long userId;

   @Column(name = "block_user_id")
   private Long blockUserId;

   @Builder
   public BlockUserPk(Long userId, Long blockUserId) {
      this.userId = userId;
      this.blockUserId = blockUserId;
   }
}
