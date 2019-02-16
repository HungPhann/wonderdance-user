package tk.wonderdance.user.helper.aws_s3.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;
import tk.wonderdance.user.helper.aws_s3.service.S3Services;

@Service
public class S3ServicesImpl implements S3Services {

    private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    public void downloadFile(String keyName) throws AmazonServiceException, AmazonClientException{
        System.out.println("Downloading an object");
        S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
        logger.info("===================== Import File - Done! =====================");

    }


    @Override
    public void uploadFile(String keyName, File uploadFile) throws AmazonServiceException, AmazonClientException{
        s3client.putObject(new PutObjectRequest(bucketName, keyName, uploadFile));
        logger.info("===================== Upload File - Done! =====================");
    }


    @Override
    public void uploadFile(String keyName, MultipartFile uploadFile) throws AmazonServiceException, AmazonClientException, IOException{
        File file = convertMultiPartToFile(uploadFile);
        s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
        file.delete();
        logger.info("===================== Upload File - Done! =====================");
    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
