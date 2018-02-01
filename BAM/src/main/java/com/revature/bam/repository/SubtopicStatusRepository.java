package com.revature.bam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.bam.bean.SubtopicStatus;

@Repository
public interface SubtopicStatusRepository extends JpaRepository<SubtopicStatus, Integer> {
	public SubtopicStatus findById(Integer id);
	public SubtopicStatus findByName(String name);
}