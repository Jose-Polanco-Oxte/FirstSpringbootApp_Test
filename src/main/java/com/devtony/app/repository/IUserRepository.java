package com.devtony.app.repository;

import com.devtony.app.model.User;
import com.devtony.app.repository.projections.PredictedUserProjection;
import com.devtony.app.repository.projections.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String username);

    @Query(value = """
    SELECT u.id AS id, u.name AS name, u.email AS email\s
    FROM user u\s
    WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :text, '%'))\s
    OR LOWER(u.email) LIKE LOWER(CONCAT('%', :text, '%'))\s
    ORDER BY\s
      CASE\s
        WHEN LOWER(u.name) LIKE LOWER(CONCAT(:text, '%')) THEN 0\s
        WHEN LOWER(u.name) LIKE LOWER(CONCAT('%', :text, '%')) THEN 1\s
        ELSE 2\s
      END,\s
      LOWER(u.name) ASC\s
    LIMIT :limit""",
            nativeQuery = true)
    List<PredictedUserProjection> findAllUsersLikeByEmailOrName(
            @Param("text") String text,
            @Param("limit") int limit);
}
