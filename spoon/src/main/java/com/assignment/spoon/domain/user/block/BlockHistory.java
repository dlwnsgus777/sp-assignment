package com.assignment.spoon.domain.user.block;

import com.assignment.spoon.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "block_histories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BlockHistory extends BaseEntity {

   @EmbeddedId
   private BlockUserPk blockUserPk;

   @Builder
   public BlockHistory(Long requestUserId, Long blockUserId) {
      this.blockUserPk = BlockUserPk.builder()
              .userId(requestUserId)
              .blockUserId(blockUserId)
              .build();
   }
}
