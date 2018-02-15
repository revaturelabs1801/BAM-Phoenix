package com.revature.bam.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.bam.bean.BamUser;
import com.revature.bam.bean.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
	public Batch findById(Integer id);
	public List<Batch> findAll();
	public List<Batch> findByTrainer(BamUser trainer);
	public List<Batch> findByEndDateGreaterThanAndStartDateLessThan(Timestamp end, Timestamp start);
}
