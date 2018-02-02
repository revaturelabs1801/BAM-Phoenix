package com.revature.bam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.bam.bean.Batch;
import com.revature.bam.bean.TopicWeek;
 
@Repository
public interface TopicWeekRepository extends JpaRepository<TopicWeek, Integer> {
	List<TopicWeek> findByBatch(Batch batch);
}