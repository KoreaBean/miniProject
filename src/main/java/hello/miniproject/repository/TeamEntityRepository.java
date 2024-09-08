package hello.miniproject.repository;

import hello.miniproject.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamEntityRepository extends JpaRepository<TeamEntity,Long> {


    boolean existsByTeamName(String teamName);
}
