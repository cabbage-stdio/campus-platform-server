package com.ruoyi.web.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;

@Service
public class OssImageService
{
    public static final String IMAGE_PATH = "/profile/oss-image";

    private static final Logger log = LoggerFactory.getLogger(OssImageService.class);
    private static final String AVATAR = "avatar";
    private static final String NOTICE = "notice";
    private static final String UUID_PATTERN = "[0-9a-f]{8}(?:-[0-9a-f]{4}){3}-[0-9a-f]{12}";
    private static final String IMAGE_SUFFIX = "\\.(?:bmp|gif|jpe?g|png)";
    private static final Pattern AVATAR_NAME = Pattern.compile("[0-9]+-" + UUID_PATTERN + IMAGE_SUFFIX);
    private static final Pattern NOTICE_NAME = Pattern.compile(UUID_PATTERN + IMAGE_SUFFIX);
    private static final Map<String, String> CONTENT_TYPES = Map.of(
            "bmp", MimeTypeUtils.IMAGE_BMP, "gif", MimeTypeUtils.IMAGE_GIF,
            "jpg", MimeTypeUtils.IMAGE_JPEG, "jpeg", MimeTypeUtils.IMAGE_JPEG,
            "png", MimeTypeUtils.IMAGE_PNG);

    private final OSS ossClient;
    private final String bucket;

    public OssImageService(OSS ossClient, @Value("${aliyun.oss.bucket}") String bucket)
    {
        this.ossClient = ossClient;
        this.bucket = bucket;
    }

    public String uploadAvatar(Long userId, MultipartFile file) throws IOException
    {
        return uploadImage(AVATAR, userId + "-" + UUID.randomUUID(), file);
    }

    public String uploadNoticeImage(MultipartFile file) throws IOException
    {
        return uploadImage(NOTICE, UUID.randomUUID().toString(), file);
    }

    private String uploadImage(String kind, String name, MultipartFile file) throws IOException
    {
        try
        {
            FileUploadUtils.assertAllowed(file, MimeTypeUtils.IMAGE_EXTENSION);
        }
        catch (InvalidExtensionException e)
        {
            throw new ServiceException("只支持 BMP、GIF、JPG、JPEG 和 PNG 图片");
        }
        String extension = FileUploadUtils.getExtension(file).toLowerCase(Locale.ROOT);
        String expectedFormat = "jpg".equals(extension) ? "jpeg" : extension;
        try (InputStream source = file.getInputStream();
                MemoryCacheImageInputStream input = new MemoryCacheImageInputStream(source))
        {
            var readers = ImageIO.getImageReaders(input);
            if (!readers.hasNext())
            {
                throw new ServiceException("请选择有效的图片文件");
            }
            var reader = readers.next();
            try
            {
                if (!expectedFormat.equalsIgnoreCase(reader.getFormatName()))
                {
                    throw new ServiceException("图片内容与文件扩展名不一致");
                }
            }
            finally
            {
                reader.dispose();
            }
        }

        String filename = name + "." + extension;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(CONTENT_TYPES.get(extension));
        metadata.setContentLength(file.getSize());
        try (InputStream input = file.getInputStream())
        {
            ossClient.putObject(new PutObjectRequest(bucket, objectKey(kind, filename), input, metadata));
        }
        return IMAGE_PATH + "/" + kind + "/" + filename;
    }

    public String getContentType(String kind, String filename)
    {
        Pattern pattern = switch (kind)
        {
            case AVATAR -> AVATAR_NAME;
            case NOTICE -> NOTICE_NAME;
            default -> null;
        };
        if (pattern == null || !pattern.matcher(filename).matches())
        {
            return null;
        }
        return CONTENT_TYPES.get(filename.substring(filename.lastIndexOf('.') + 1));
    }

    public OSSObject openImage(String kind, String filename)
    {
        return ossClient.getObject(bucket, objectKey(kind, filename));
    }

    public void deleteAvatar(Long userId, String path)
    {
        String prefix = IMAGE_PATH + "/" + AVATAR + "/";
        if (path == null || !path.startsWith(prefix + userId + "-"))
        {
            return;
        }
        String filename = path.substring(prefix.length());
        if (AVATAR_NAME.matcher(filename).matches())
        {
            try
            {
                ossClient.deleteObject(bucket, objectKey(AVATAR, filename));
            }
            catch (RuntimeException e)
            {
                log.warn("清理 OSS 头像失败，userId={}", userId, e);
            }
        }
    }

    private String objectKey(String kind, String filename)
    {
        return "upload/" + kind + "/" + filename;
    }
}
