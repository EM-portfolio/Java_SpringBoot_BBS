package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    Optional<Reply> findById(int id);
    void deleteById(int id);
    List<Reply> findByParentBoad_Id(int parentBoadId);
    List<Reply> findByParentBoad(BulletinBoad parentBoad);
}
