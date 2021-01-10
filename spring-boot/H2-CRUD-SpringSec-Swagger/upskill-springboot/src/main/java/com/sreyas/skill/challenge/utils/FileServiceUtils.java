package com.sreyas.skill.challenge.utils;

import lombok.extern.slf4j.Slf4j;
import com.sreyas.skill.challenge.config.ApplicationProperties;
import com.sreyas.skill.challenge.exception.AdminServiceException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Provides zip, upload and delete for files.
 * Author : Sreyas V Pariyath
 * Date   : 08/12/20
 * Time   : 11:35 PM
 */
@Slf4j
@Component
public class FileServiceUtils {

    private final RestTemplate restTemplate;

    private final ApplicationProperties properties;

    public FileServiceUtils(RestTemplate restTemplate, ApplicationProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    /**
     * zips and uploads the given file to portal
     *
     * @param file
     */
    public void upload(File file) {
        if (file.exists()) {
            String fileName = file.getName();
            String parentPath = file.getParentFile().getAbsolutePath();
            File zipFile = Paths.get(file.getParentFile().getAbsolutePath(), fileName.concat(".zip")).toFile();
            List<String> fNameInList = Arrays.asList(file.listFiles()).stream().map(f -> f.getName())
                    .collect(Collectors.toList());
            log.info(" [FSU] file names to be zipped {}", fNameInList);
            zipFile(parentPath + "/" + fileName, fNameInList, zipFile.getAbsolutePath());
            uploadToPortal(zipFile);
        } else {
            log.error(" [FSU] File not found..{}", file.getAbsolutePath());
            throw new RuntimeException(String.format("File not Found %s", file.getAbsolutePath()));
        }
    }

    /**
     * Zips the given folder
     *
     * @param folderName
     * @param fileList
     * @param zipFileLocation
     */
    private void zipFile(String folderName, List<String> fileList, String zipFileLocation) {

        byte[] buffer = new byte[1024];
        try {
            FileOutputStream fos = new FileOutputStream(zipFileLocation);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (String file : fileList) {
                ZipEntry ze = new ZipEntry(file);
                zos.putNextEntry(ze);
                FileInputStream in = new FileInputStream(folderName + File.separator + file);
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                in.close();
            }
            zos.closeEntry();
            zos.close();
            log.info(" [FSU] zip file has been created");
        } catch (IOException ex) {
            log.error(" [FSU] Error while Zipping the file..", ex);
        }
    }

    /**
     * Uploads the file
     *
     * @param file
     */
    private void uploadToPortal(File file) {
        try {
            HttpHeaders headers = new HttpHeaders();
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new FileSystemResource(file));
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
            final String uploadUrl = properties.getPortalUrl();
            restTemplate.postForEntity(uploadUrl, entity, Void.class);
        } catch (Exception ex) {
            log.error(" [FSU] Error while Connecting to portal..", ex);
            throw new AdminServiceException();
        }
    }

    /**
     * deletes the given file or folder
     *
     * @param folder
     */
    public void delete(File folder) {
        try {
            if (folder.isDirectory()) {
                File[] files = folder.listFiles();
                for (File file : files) {
                    file.delete();
                }
            }
            folder.delete();
        } catch (Exception e) {
            log.error(" [FSU] Error while deleting the file", e);
        }
    }
}
