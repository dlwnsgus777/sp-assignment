package com.assignment.spoon.domain.user.block;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class BlockUserPk implements Serializable {
   private Long userId;
   private Long blockUserId;
}
