package tk.wonderdance.user.helper.aws_s3.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface S3Services {
    public void downloadFile(String keyName) throws AmazonServiceException, AmazonClientException;
    public void uploadFile(String keyName, File uploadFile) throws AmazonServiceException, AmazonClientException;
    public void uploadFile(String keyName, MultipartFile uploadFile) throws AmazonServiceException, AmazonClientException, IOException;
}
