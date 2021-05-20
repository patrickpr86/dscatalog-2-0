package com.patrick.dscatalog.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


import org.apache.commons.io.FilenameUtils;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {

	private static Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	public URL uploadFile(MultipartFile file) {
		try {
			
			String originalName = file.getOriginalFilename();
			String extensionName = FilenameUtils.getExtension(originalName);
			String fileName = Instant.now().toDate().getTime() + "." + extensionName;
			
			InputStream inputStream = file.getInputStream();
			String contentType = file.getContentType();				
			return uploadFile(inputStream, contentType, fileName);
			
			
		}
		catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	private URL uploadFile(InputStream inputStream, String contentType, String fileName) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(contentType);
		LOG.info("Upload start");
		s3client.putObject(bucketName, fileName, inputStream, metadata);
		LOG.info("Upload end");
		return s3client.getUrl(bucketName, fileName);
		
	}
}
