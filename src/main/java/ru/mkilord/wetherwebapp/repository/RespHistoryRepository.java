package ru.mkilord.wetherwebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mkilord.wetherwebapp.model.Response;

public interface RespHistoryRepository extends JpaRepository<Response, Long> {
}
