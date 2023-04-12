package com.kakao.jPanda.bsm.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kakao.jPanda.bsm.dao.TalentDao;
import com.kakao.jPanda.bsm.domain.Category;
import com.kakao.jPanda.bsm.domain.Talent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TalentServiceImpl implements TalentService{
	private final TalentDao dao;
	
	@Override
	public List<Category> categoryList() {
		List<Category> categoryList = dao.categoryList();
		System.out.println("TalentDao.categoryList() categoryList.size() -> " + categoryList.size());
		return categoryList;
	}
	
	@Override
	public void talentUpload(Talent talent) {
		dao.talentUpload(talent);
	}
	
	@Override
	public ModelAndView contentImageUpload(MultipartHttpServletRequest request) {
		// 이미지 업로드시 10mb이하 크기만 업로드 가능
		long maxFileSize = 10 * 1024 * 1024; // 10mb
		if (request.getFile("upload").getSize() > maxFileSize) {
		    return null;
		}
		
		// ckeditor는 이미지 업로드 후 이미지 표시하기 위해 uploaded 와 url을 json 형식으로 받아야 함
		// modelandview를 사용하여 json 형식으로 보내기위해 모델앤뷰 생성자 매개변수로 jsonView 라고 써줌
		// jsonView 라고 쓴다고 무조건 json 형식으로 가는건 아니고 @Configuration 어노테이션을 단 
		// WebConfig 파일에 MappingJackson2JsonView 객체를 리턴하는 jsonView 매서드를 만들어서 bean으로 등록해야 함 
		ModelAndView mav = new ModelAndView("jsonView");

		// ckeditor 에서 파일을 보낼 때 upload : [파일] 형식으로 해서 넘어오기 때문에 upload라는 키의 밸류를 받아서 uploadFile에 저장함
		MultipartFile uploadFile = request.getFile("upload");
		
		// 파일의 오리지널 네임
		String originalFileName = uploadFile.getOriginalFilename();
		System.out.println("MainController.image() 파일의 오리지널 네임 -> " + originalFileName);
		
        // 파일의 확장자 추출
		String ext = originalFileName.substring(originalFileName.indexOf("."));
		System.out.println("MainController.image() 파일의 확장자 -> " + ext);
		
        // 서버에 저장될 때 중복된 파일 이름인 경우를 방지하기 위해 UUID에 확장자를 붙여 새로운 파일 이름을 생성
		String newFileName = UUID.randomUUID() + ext;
		System.out.println("MainController.image() 서버에 저장될 파일 이름 -> " + newFileName);

		// 이미지를 현재 경로와 연관된 파일에 저장하기 위해 현재 경로를 알아냄
		String realPath = request.getServletContext().getRealPath("/contentImage/");
		System.out.println("MainController.image() 현재 파일 경로 -> " + realPath);

		// 현재경로/upload/파일명이 저장 경로
		String savePath = realPath + newFileName;
		System.out.println("MainController.image() 파일 저장 경로 + 파일 이름 -> " + savePath);
		
		// 해당 파일 경로에 폴더가 없을시 폴더 생성
		File fileDirectory = new File(savePath);
		if(!fileDirectory.exists()) {
			// 신규 폴더(Directory)생성
			fileDirectory.mkdirs();
		}
		
		// 브라우저에서 이미지 불러올 때 절대 경로로 불러오면 보안의 위험 있어 상대경로를 쓰거나 이미지 불러오는 jsp 또는 클래스 파일을 만들어 가져오는 식으로 우회해야 함
		// 때문에 savePath와 별개로 상대 경로인 uploadPath 만들어줌
		String uploadPath = "./contentImage/" + newFileName; 
		System.out.println("MainController.image() 보안을 위한 상대 경로 출력 -> " + uploadPath);

		// 저장 경로로 파일 객체 생성
		File file = new File(savePath);

		// 파일 업로드
		try {
			uploadFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// uploaded, url 값을 modelandview를 통해 보냄
		mav.addObject("uploaded", true); // 업로드 완료
		mav.addObject("url", uploadPath); // 업로드 파일의 경로 // 일단 절대경로

		return mav;
	}
	
	@Override
	public ModelAndView mainImageUpload(MultipartFile file, HttpServletRequest request) {
		 // 이미지 업로드시 10mb이하 크기만 업로드 가능
        long maxFileSize = 10 * 1024 * 1024; // 10mb
        if (file.getSize() > maxFileSize) {
            return null;
        }
        
        ModelAndView mav = new ModelAndView("jsonView");

        // 파일의 오리지널 네임
        String originalFileName = file.getOriginalFilename();
        
        // 파일의 확장자 추출
        String ext = originalFileName.substring(originalFileName.indexOf("."));

        // 서버에 저장될 때 중복된 파일 이름인 경우를 방지하기 위해 UUID에 확장자를 붙여 새로운 파일 이름을 생성
        String newFileName = UUID.randomUUID() + ext;

        // 이미지를 현재 경로와 연관된 파일에 저장하기 위해 현재 경로를 알아냄
        String realPath = request.getServletContext().getRealPath("/mainImage/");
        
        // 해당 파일 경로에 폴더가 없을시 폴더 생성
     		File fileDirectory = new File(realPath);
     		if(!fileDirectory.exists()) {
     			// 신규 폴더(Directory)생성
     			fileDirectory.mkdirs();
     		}
        
        // 현재경로/upload/파일명이 저장 경로
        String savePath = realPath + newFileName;
        System.out.println("Controller.uploadImage() 파일 저장 경로 + 파일 이름 -> " + savePath);

        // 브라우저에서 이미지 불러올 때 절대 경로로 불러오면 보안의 위험 있어 상대경로를 쓰거나 이미지 불러오는 jsp 또는 클래스 파일을 만들어 가져오는 식으로 우회해야 함
        // 때문에 savePath와 별개로 상대 경로인 uploadPath 만들어줌
        String uploadPath = "./mainImage/" + newFileName;
        System.out.println("Controller.uploadImage() 보안을 위한 상대 경로 출력 -> " + uploadPath);

        // 저장 경로로 파일 객체 생성
        File saveFile = new File(savePath);

        // 파일 업로드
        try {
			file.transferTo(saveFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

        // uploaded, url 값을 modelandview를 통해 보냄
        mav.addObject("uploaded", true); // 업로드 완료
        mav.addObject("url", uploadPath); // 업로드 파일의 경로 // 일단 절대경로

        return mav;
	}
	
}
