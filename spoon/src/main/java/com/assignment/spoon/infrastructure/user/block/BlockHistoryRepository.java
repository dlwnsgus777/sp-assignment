package com.assignment.spoon.infrastructure.user.block;

import com.assignment.spoon.domain.user.block.BlockHistory;
import com.assignment.spoon.domain.user.block.BlockUserPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockHistoryRepository extends JpaRepository<BlockHistory, BlockUserPk> {
    Optional<BlockHistory> findByBlockUserPk_UserIdAndBlockUserPk_BlockUserId(Long requestUserId, Long blockUserId);
}
