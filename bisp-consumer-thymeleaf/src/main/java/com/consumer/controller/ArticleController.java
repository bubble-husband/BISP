package com.consumer.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.commons.annotations.PassToken;
import com.commons.entity.Article;
import com.github.pagehelper.PageInfo;

@Controller
public class ArticleController {

	private static final String REST_URL_PREFIX = "http://localhost:8001";

	@Autowired
	private RestTemplate restTemplate;

	// 去添加
	@PassToken
	@GetMapping("/consumer/article/toAdd")
	public String toAddArticle() {
		return "Article_add";
	}

	// 去修改
	@GetMapping("/consumer/article/toUpdate")
	public String toUpdateArticle(@RequestParam Integer articleId, Model model) {
		Article article = restTemplate.getForObject(REST_URL_PREFIX + "/article/selectById/" + articleId,
				Article.class);
		model.addAttribute("article", article);
		return "Article_edit";
	}

	// 查询所有
	@PassToken
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/article/list")
	public String list(Model model) {
		List<Article> list = restTemplate.getForObject(REST_URL_PREFIX + "/article/list", List.class);
		model.addAttribute("article", list);
		return "Article_list";
	}

	// 根据ID查找一条记录
	@PassToken
	@GetMapping("/consumer/article/selectById")
	public String selectById(@RequestParam Integer articleId, Model model) {
		Article article = restTemplate.getForObject(REST_URL_PREFIX + "/article/selectById/" + articleId,
				Article.class);
		model.addAttribute("article", article);
		return "Article_byid";
	}
	//根据ID查找一条记录（ajax，用于前端页面调用）
	@GetMapping("/consumer/article/selectByIdOne")
	@ResponseBody
	public Article selectById1(@RequestParam Integer articleId) {
		Article article = restTemplate.getForObject(REST_URL_PREFIX + "/article/selectByIdOne/" + articleId,
				Article.class);
		return article;
	}
	

	// 添加一条记录
	@PassToken
	@SuppressWarnings("unchecked")
	@PostMapping("/consumer/article/add")
	public String addArticle(@PathVariable("file") MultipartFile file, HttpServletRequest request, Article article,
			Model m) throws Exception {
		article.setClickNum(0);
		article.setCollectNum(0);
		article.setCommentNum(0);
		// 得到文件上传的路径
		String path = request.getServletContext().getRealPath("/upload");
		// 如果此路径不存在，则创建
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 上传文件的名称生成策略，为了不重名 首选截取后缀名
		file.getOriginalFilename();
		// 获取是完整文件名 整个就是得到以.后面的字符串
		String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		// 最后生成的文件名就是以uuid生成随机的+后缀
		String fileName = UUID.randomUUID().toString() + fileSuffix;
		File local = new File(this.getClass().getResource("/").getPath()); 
		System.out.println(local);
		File f = new File(
				"D:\\EclipseWorkspace\\BISP\\bisp-consumer-thymeleaf\\src\\main\\resources\\static\\img\\"
						+ fileName);
		// 最后上传
		System.out.println(f);
		file.transferTo(f);
		article.setTitlePicture(fileName);
		System.out.println(article.getTitlePicture());
		restTemplate.postForObject(REST_URL_PREFIX + "/article/add", article, int.class);
		List<Article> list = restTemplate.getForObject(REST_URL_PREFIX + "/article/list", List.class);
		m.addAttribute("article", list);
		return "Article_list";
	}

	// 删除一条记录
	@PassToken
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/article/delete")
	public String deleteArticle(Integer articleId, Model model) {
		restTemplate.getForObject(REST_URL_PREFIX + "/article/delete/" + articleId, String.class);
		List<Article> list = restTemplate.getForObject(REST_URL_PREFIX + "/article/list", List.class);
		model.addAttribute("article", list);

		return "Article_list";
	}

	// 更新一条记录
	@PassToken
	@SuppressWarnings("unchecked")
	@PostMapping("/consumer/article/update")
	public String updateArticle(Article article, Model model) {
		restTemplate.postForObject(REST_URL_PREFIX + "/article/update/", article, String.class);
		List<Article> list = restTemplate.getForObject(REST_URL_PREFIX + "/article/list", List.class);
		model.addAttribute("article", list);
		return "Article_list";
	}
	
	//----------6.18----------
	//文件上传
	@PostMapping("upload.do")
	@PassToken
	@ResponseBody
	public String update(MultipartFile file, HttpServletRequest request) throws FileNotFoundException{
		String path = ResourceUtils.getURL("classpath:").getPath().replace("%20"," ").replace('/', '\\')+"static\\titlePicture";
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		file.getOriginalFilename();
		String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String fileName = UUID.randomUUID().toString()+fileSuffix;
		File f = new File(dir+"/"+fileName);
		try {
			file.transferTo(f);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fullPath = dir + "\\" + fileName;
		String relativePath = "/titlePicture/" + fileName;
		String result="{\"fullPath\":\""+fullPath+"\", \"relativePath\":\""+relativePath+"\"}";
		System.out.println(f);
		return result;  //6.16
	}
	
	//分页查询
	@PassToken
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/article/listPage")
	public String listPage(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, Model model) {
		//引入PageHelper插件
	    //在使用pageHelper插件在查询之前只需调用，startPage，传入页码pageNum，设置数据显示多少
		PageInfo<Article> pageInfo = restTemplate.getForObject(REST_URL_PREFIX + "/article/listPage/"+pageNum, PageInfo.class);
		model.addAttribute("pageInfo", pageInfo);
		return "ArticleTable";
	}

}
