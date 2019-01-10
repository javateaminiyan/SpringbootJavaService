package com.restservice.restservicejavateam.repo;

import com.restservice.restservicejavateam.domain.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepository extends JpaRepository<Upload,Long> {


}
