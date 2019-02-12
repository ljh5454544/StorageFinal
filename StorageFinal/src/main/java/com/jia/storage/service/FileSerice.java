package com.jia.storage.service;

import com.jia.storage.entity.Commodity;
import com.jia.storage.entity.Tenant;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileSerice {

    List<Commodity> commFileRes(MultipartFile file) throws IOException;

    List<Tenant> tenantFileRes(MultipartFile file) throws IOException;
}
