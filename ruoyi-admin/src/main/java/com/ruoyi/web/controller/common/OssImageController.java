package com.ruoyi.web.controller.common;

import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.ruoyi.web.core.OssImageService;

@RestController
@RequestMapping(OssImageService.IMAGE_PATH)
public class OssImageController
{
    private static final Logger log = LoggerFactory.getLogger(OssImageController.class);
    private final OssImageService imageService;

    public OssImageController(OssImageService imageService)
    {
        this.imageService = imageService;
    }

    @GetMapping("/{kind}/{filename}")
    public void image(@PathVariable String kind, @PathVariable String filename, HttpServletResponse response)
            throws IOException
    {
        String contentType = imageService.getContentType(kind, filename);
        if (contentType == null)
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try (OSSObject object = imageService.openImage(kind, filename))
        {
            response.setContentType(contentType);
            response.setContentLengthLong(object.getObjectMetadata().getContentLength());
            response.setHeader("Cache-Control", "public, max-age=3600");
            response.setHeader("X-Content-Type-Options", "nosniff");
            object.getObjectContent().transferTo(response.getOutputStream());
        }
        catch (OSSException e)
        {
            if ("NoSuchKey".equals(e.getErrorCode()))
            {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            log.warn("读取 OSS 图片失败，错误码={}", e.getErrorCode());
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
        catch (ClientException e)
        {
            log.warn("连接 OSS 失败", e);
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        }
    }
}
