package com.assignment.spoon.infrastructure.user.fan;

import com.assignment.spoon.domain.user.fan.Fan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FanRepository extends JpaRepository<Fan, Long> {
    List<Fan> findByDjId(long djId);
}
