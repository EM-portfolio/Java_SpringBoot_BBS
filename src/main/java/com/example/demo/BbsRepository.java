package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.BulletinBoad;
import java.util.Optional;

@Repository
public interface BbsRepository extends JpaRepository<BulletinBoad, Integer> {

	Optional<BulletinBoad> findById(int id);
	public void deleteById(int id);
	
}
