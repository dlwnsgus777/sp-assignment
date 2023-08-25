package com.assignment.spoon.infrastructure.user.block;

import com.assignment.spoon.domain.user.block.BlockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockHistoryRepository extends JpaRepository<BlockHistory, Long> {
}
