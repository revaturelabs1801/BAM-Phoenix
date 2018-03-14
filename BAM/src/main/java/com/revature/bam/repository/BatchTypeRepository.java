package com.revature.bam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.bam.bean.BatchType;

public interface BatchTypeRepository extends JpaRepository<BatchType, Integer> {
	public List<BatchType> findAll();
}
