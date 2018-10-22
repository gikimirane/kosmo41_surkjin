package com.study.spring;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {
	
	@RequestMapping("/uploadForm")
	public String uploadForm() {
		
		return "FileUpload/uploadForm";
	}
	
	@RequestMapping("/uploadPath")
	public void uploadPath(HttpServletResponse resp, HttpServletRequest req) 
				throws IOException{
		String path = req.getSession().getServletContext().getRealPath("resources/upload");
		
		resp.setContentType("text/html: charset=UTF-8"); 
		PrintWriter print = resp.getWriter();
		print.println("/upload폴더의 물리적 경로");
		print.print(path);
	}
	
	@RequestMapping("/uploadList")
	public String uploadList(HttpServletRequest req, Model model) 
			throws IOException {
		String saveDirectory = 
				req.getSession().getServletContext().getRealPath("resources/upload");
		File file = new File(saveDirectory);
		
		File[] files = file.listFiles();
		
		Map<String, Integer> fileMap = new HashMap<String, Integer>();
		
		for(File f : files) {
			fileMap.put(f.getName(), (int)Math.ceil(f.length()/1024.0));
		}
		
		model.addAttribute("fileMap", fileMap);
		return "FileUpload/uploadList";
	}
	
	// 파일 다운로드	
		@RequestMapping("/download")	
		public ModelAndView download(HttpServletRequest req, 
	            HttpServletResponse resp) throws Exception {
		    		
			System.out.println("FundingController의 callDownload호출 성공");
		    
			String fileName = req.getParameter("fileName");
			String oriFileName = req.getParameter("oriFileName");
		    
		    String saveDirectory = 
		    		req.getSession().getServletContext().getRealPath("/resources/upload");
		    
		    File downloadFile = new File(saveDirectory+"/"+fileName);
		    
		    if(!downloadFile.canRead()){
		        throw new Exception("File can't read(파일을 찾을 수 없습니다)");
		    }
		    
		    ModelAndView mv = new ModelAndView();
		    mv.setViewName("fileDownloadView");
		  	mv.addObject("downloadFile", downloadFile);
		  	mv.addObject("oriFileName", oriFileName);
		  		 
		  	return mv;
		  	//return new ModelAndView("뷰", "속성명", "값");
		}	
		
		// 파일 업로드 처리
		@RequestMapping("/uploadAction")
		public String uploadAction(HttpServletRequest req, Model model){
			
			System.out.println("파일업로드 진행중");
			
			//서버의 물리적경로 가져오기
			String path = 
				req.getSession().getServletContext().getRealPath("/resources/upload");
			
			//뷰로 전달할 정보를 저장하기 위한 Map타입의 변수
			Map returnObj = new HashMap();
			
			try{
				/*
				 * 파일업로드 위한 MultipartHttpServletRequest객체 생성
				 * 객체 생성과 동시에 파일업로드 완료됨. 나머지 폼값은 Multipart가 
				 * 통째로 받아서 처리한다.
				 */
				MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) req;
				
				//업로드폼의 file속성 필드의 이름을 모두 읽음
				Iterator itr = mhsr.getFileNames();
				
				MultipartFile mfile = null;			
				String fileName = "";		
				
				//파일 하나의 정보를 저장하기 위한 List타입의 변수(원본파일명, 저장된파일명 등)
				List resultList = new ArrayList();
				
				//폼값받기 : 제목
				String title = mhsr.getParameter("title");
					
				File directory = new File(path);
				//업로드할 디렉토리가 있는지 확인후
				if(!directory.isDirectory()){
					//디렉토리가 없다면 생성함
					directory.mkdirs();
				}
				
				//업로드폼의 file속성의 필드의 갯수만큼 반복
				while(itr.hasNext()){
					
					//userfile1, userfile2....출력됨
					fileName = (String)itr.next();
					//System.out.println(fileName);	
					
					//서버로 업로드된 임시파일명 가져옴
					mfile = mhsr.getFile(fileName);
					//System.out.println(mfile);//CommonsMultipartFile@1366c0b 형태임
					
					//한글깨짐방지 처리후 업로드된 파일명을 가져온다.
					String originalName = 
						new String(mfile.getOriginalFilename().getBytes(),"UTF-8");
					
					//파일명이 공백이라면 while문의 처음으로 돌아간다.
					if("".equals(originalName)){
						continue;
					}
					//System.out.println("originalName:"+originalName);

					//파일명에서 확장자 가져오기
					String ext = originalName.substring(originalName.lastIndexOf('.'));
					
					//파일명을 UUID로 생성된값으로 변경함.
					String saveFileName = getUuid() + ext;
					
					//설정한 경로에 파일저장
					File serverFullName = new File(path + File.separator + saveFileName);
					
					//업로드한 파일을 지정한 파일에 저장한다.
					mfile.transferTo(serverFullName);
					
					Map file = new HashMap();
					file.put("originalName", originalName);//원본파일명
					file.put("saveFileName", saveFileName);//저장된파일명
					file.put("serverFullName", serverFullName);//서버에 저장된 전체경로 및 파일명
					file.put("title", title);//타이틀
					
					//위 파일의 정보를 담은 Map을 List에 저장
					resultList.add(file);
				}
				returnObj.put("files", resultList);
			}
			catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
			catch(IllegalStateException e){
				e.printStackTrace();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
			model.addAttribute("returnObj", returnObj);	
			
			return "FileUpload/uploadAction";
		}
		
		// uuid 생성할 메소드 선언
		/*
		 * UUID(Universally unique identifier), 범용 고유 식별자.
		 * UUID.randomUUID().toString() 으로 생성하면 
		 * b7389ffc-eca7-40cc-b09b-d46cfdc095bd
		 * 와 같이 4개의 하이픈과 32개의 문자로 이루어진 문자열을 반환한다.
		 */
		public static String getUuid(){
			String uuid = UUID.randomUUID().toString();
			//System.out.println(uuid);		
			uuid = uuid.replaceAll("-", "");
			//System.out.println("생성된UUID:"+ uuid);
			return uuid;
		}	

}
