package com.sample.uploads.repository;

import com.sample.uploads.model.UploadsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UploadsRepo extends JpaRepository<UploadsData, Long> {



}
