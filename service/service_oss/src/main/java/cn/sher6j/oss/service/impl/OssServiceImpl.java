package cn.sher6j.oss.service.impl;

import cn.sher6j.oss.service.OssService;
import cn.sher6j.oss.utils.ConstantPropertiesUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author sher6j
 * @create 2020-05-17-14:48
 */
@Service
public class OssServiceImpl implements OssService {
    /**
     * 上传头像文件到OSS
     * @param file
     * @return
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        OSS ossClient = null;

        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 获取上传文件流。
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String fileName = file.getOriginalFilename();

            // 在文件名称里面添加随机唯一的值（否则如果上传相同名称的文件会进行覆盖）
            // 生成的uuid中有很多"-"，把"-"去掉
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid+fileName;

            /**
             * 调用OSS方法实现上传，并把文件按照日期进行分类 2020/05/17/....jpg
             * 1 Bucket名称
             * 2 上传到OSS文件路径和文件名称  文件路径/文件名称
             * 3 上传文件输入流
             */
            String date = new DateTime().toString("yyyy/MM/dd");
            fileName  = date + "/" + fileName;
            ossClient.putObject(bucketName, fileName, inputStream);

            //把上传之后文件路径返回
            //示例https://edu-2020-05.oss-cn-beijing.aliyuncs.com/1.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }
}
