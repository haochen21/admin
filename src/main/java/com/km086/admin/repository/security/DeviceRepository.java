package com.km086.admin.repository.security;

import com.km086.admin.model.security.Device;
import com.km086.admin.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends BaseRepository<Device, Long> {

    @Query("select d from Device d where d.no = :no")
    Device findByNo(@Param("no") String paramString);

}


