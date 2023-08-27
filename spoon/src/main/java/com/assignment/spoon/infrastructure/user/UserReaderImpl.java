package com.assignment.spoon.infrastructure.user;

import com.assignment.spoon.domain.user.User;
import com.assignment.spoon.domain.user.UserDto;
import com.assignment.spoon.domain.user.UserReader;
import com.assignment.spoon.domain.user.block.BlockHistory;
import com.assignment.spoon.domain.user.fan.Fan;
import com.assignment.spoon.infrastructure.user.block.BlockHistoryRepository;
import com.assignment.spoon.infrastructure.user.fan.FanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {
    private final UserRepository userRepository;
    private final FanRepository fanRepository;
    private final JdbcTemplate jdbcTemplate;
    private final BlockHistoryRepository blockHistoryRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    @Override
    public Fan getFanByFollowerId(Long djId, Long followerId) {
        return fanRepository.findByDjIdAndFollowerId(djId, followerId)
              .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팬입니다."));
    }

    @Override
    public Optional<Fan> findFanByFollowerId(Long djId, Long followerId) {
        return fanRepository.findByDjIdAndFollowerId(djId, followerId);
    }

    @Override
    public BlockHistory getBlockUser(Long requestUserId, Long blockUserId) {
        return null;
    }

    @Override
    public Optional<BlockHistory> findBlockUser(Long requestUserId, Long blockUserId) {
        return blockHistoryRepository.findByBlockUserPk_UserIdAndBlockUserPk_BlockUserId(requestUserId, blockUserId);
    }

    @Override
    public UserDto.Main getUser(Long userId) {
        List<UserDto.Main> results = jdbcTemplate.query(
                "SELECT u.*, COUNT(f.id) AS fan_count " +
                        "FROM users u " +
                        "LEFT JOIN fans f ON u.id = f.dj_id " +
                        "WHERE u.id = ? " +
                        "GROUP BY u.id",
                mapper,
                userId
        );

        if (results.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }

        return results.get(0);
    }

    static RowMapper<UserDto.Main> mapper = (rs, rowNum) ->
                    UserDto.Main.builder()
                            .id(rs.getLong("id"))
                            .email(rs.getString("email"))
                            .status(User.Status.valueOf(rs.getString("status")))
                            .createdAt(dateTimeOf(rs.getTimestamp("created_at")))
                            .fanCount(rs.getLong("fan_count"))
                            .build();


    private static LocalDateTime dateTimeOf(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
