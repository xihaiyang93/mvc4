package com.tgb.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping("/upload")
public class UploadCotroller {
	/*
	 * 采用spring提供的上传文件的方法
	 */
	@RequestMapping(value = "/springUpload", method = RequestMethod.POST)
	public void springUpload(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {
		long startTime = System.currentTimeMillis();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					String fileName = file.getOriginalFilename();
					String path = "F:/upload/" + fileName;
					// 上传
					file.transferTo(new File(path));
				}

			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("方法三的运行时间：" + String.valueOf(endTime - startTime) + "ms");

		String result = "{\"result\":\"success\"}";
		response.setContentType("application/json");
		response.getWriter().write(result);
	}

	/*
	 * 采用spring提供的上传多文件的方法
	 */
	@RequestMapping(value = "/multipartUpload", method = RequestMethod.POST)
	public void multipartUpload(HttpServletRequest request,HttpServletResponse response, @RequestParam("file") MultipartFile[] files)
			throws IllegalStateException, IOException {
		long startTime = System.currentTimeMillis();
		CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());

		if (cmr.isMultipart(request)) {
			for (int i = 0; i < files.length; i++) {
				MultipartFile mFile = files[i];
				if (mFile != null) {
					String fileName = mFile.getOriginalFilename();
					String path = "F:/upload/" + fileName;

					File localFile = new File(path);
					mFile.transferTo(localFile);
					request.setAttribute("fileUrl", path);
				}
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("方法三的运行时间：" + String.valueOf(endTime - startTime) + "ms");
		
		String result = "{\"result\":\"success\"}";
		response.setContentType("application/json");
		response.getWriter().write(result);
	}
	
	 /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    private boolean checkFile(String fileName) {
        //设置允许上传文件类型
        String suffixList = "jpg,gif,png,ico,bmp,jpeg";
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")
                + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }
}
