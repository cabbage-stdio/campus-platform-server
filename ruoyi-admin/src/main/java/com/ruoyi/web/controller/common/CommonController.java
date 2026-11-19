package com.ruoyi.web.controller.common;

import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;

/**
 * 通用请求处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/common")
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private OSS ossClient;

    @Value("${aliyun.oss.bucket}")
    private String ossBucket;

    private static final String FILE_DELIMITER = ",";

    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return AjaxResult.error("请选择要上传的文件");
        }

        FileUploadUtils.assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);

        String extension = FileUploadUtils.getExtension(file);
        String newFileName = UUID.randomUUID() + "." + extension;
        String objectKey = "upload/" + LocalDate.now() + "/" + newFileName;

        try (InputStream input = file.getInputStream()) {
            ossClient.putObject(new PutObjectRequest(ossBucket, objectKey, input));
        }

        Date expiresAt = Date.from(Instant.now().plus(Duration.ofMinutes(10)));
        String url = ossClient.generatePresignedUrl(ossBucket, objectKey, expiresAt)
                .toExternalForm();

        AjaxResult result = AjaxResult.success();
        result.put("fileName", objectKey);
        result.put("newFileName", newFileName);
        result.put("originalFilename", file.getOriginalFilename());
        result.put("url", url);
        return result;
    }

    @PostMapping("/uploads")
    public AjaxResult uploadFiles(@RequestParam("files") List<MultipartFile> files)
            throws Exception {
        if (files.isEmpty()) {
            return AjaxResult.error("请选择要上传的文件");
        }

        // 先校验整批，避免上传到一半才发现某张图片不合规
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                return AjaxResult.error("上传文件中包含空文件");
            }
            FileUploadUtils.assertAllowed(file, MimeTypeUtils.IMAGE_EXTENSION);
        }

        List<String> urls = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        List<String> newFileNames = new ArrayList<>();
        List<String> originalFilenames = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                String newFileName = UUID.randomUUID() + "." + FileUploadUtils.getExtension(file);
                String objectKey = "upload/" + LocalDate.now() + "/" + newFileName;

                try (InputStream input = file.getInputStream()) {
                    fileNames.add(objectKey);
                    ossClient.putObject(new PutObjectRequest(ossBucket, objectKey, input));
                }

                newFileNames.add(newFileName);
                originalFilenames.add(file.getOriginalFilename());
            }

            Date expiresAt = Date.from(Instant.now().plus(Duration.ofMinutes(10)));
            for (String objectKey : fileNames) {
                urls.add(ossClient.generatePresignedUrl(ossBucket, objectKey, expiresAt)
                        .toExternalForm());
            }
        } catch (Exception e) {
            log.error("批量上传图片失败", e);
            for (String objectKey : fileNames) {
                try {
                    ossClient.deleteObject(ossBucket, objectKey);
                } catch (RuntimeException cleanupError) {
                    log.warn("清理批量上传失败的 OSS 对象失败，objectKey={}", objectKey, cleanupError);
                }
            }
            return AjaxResult.error("批量上传失败，请重试");
        }

        AjaxResult result = AjaxResult.success();
        result.put("fileNames", StringUtils.join(fileNames, FILE_DELIMITER));
        result.put("urls", StringUtils.join(urls, FILE_DELIMITER));
        result.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMITER));
        result.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMITER));
        return result;
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        try
        {
            if (!FileUtils.checkAllowDownload(resource))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + FileUtils.stripPrefix(resource);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }


}
