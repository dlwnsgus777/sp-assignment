package com.assignment.spoon.domain.user.fan;

import com.assignment.spoon.domain.BaseEntity;
import com.assignment.spoon.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fans")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Fan extends BaseEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "dj_id", referencedColumnName = "id")
   private User dj;

   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "follower_id", referencedColumnName = "id")
   private User follower;

   @Builder
   public Fan(Long id, User dj, User follower) {
      this.id = id;
      this.dj = dj;
      this.follower = follower;
   }
}
