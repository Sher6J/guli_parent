package cn.sher6j.oss.controller;

import cn.sher6j.commonutils.R;
import cn.sher6j.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sher6j
 * @create 2020-05-17-14:48
 */
@Api(description = "阿里云OSS服务")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin //解决跨域
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像图片文件
     * @param file
     * @return
     */
    @ApiOperation(value = "上传头像文件")
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        //获取上传文件
        //上传文件方法返回头像图片在阿里云OSS服务中的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url", url);
    }
}
