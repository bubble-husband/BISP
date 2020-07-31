package com.consumer.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.commons.annotations.PassToken;
import com.commons.entity.Userdetail;

@Controller
public class UserDetailController {

	@Autowired
	private RestTemplate restTemplate;
	private static final String REST_URL_PREFIX = "http://localhost:8001";

	@PassToken
	@SuppressWarnings("unchecked")
	@GetMapping("/consumer/userDetail/selectAll")
	public String selectAll(Model m) {
		List<Userdetail> list = restTemplate.getForObject(REST_URL_PREFIX + "/userDetail/selectAll", List.class);
		m.addAttribute("allList", list);
		return "selectAllUserDetail";
	}

	@PassToken
	@RequestMapping("/consumer/userDetail/deleteById")
	public String deleteById(int userdetailId) {
		restTemplate.postForObject(REST_URL_PREFIX + "/userDetail/deleteById", userdetailId, int.class);
		return "redirect:selectAll";
	}

	// 点击修改跳转到编辑页面
	@PassToken
	@GetMapping("/consumer/userDetail/editUpdate")
	public String editUpdate(int userdetailId, Model m) {
		int id = userdetailId;
		Userdetail userDetail = restTemplate.getForObject(REST_URL_PREFIX + "/userDetail/selectById/" + id,
				Userdetail.class);
		m.addAttribute("userDetail", userDetail);
		return "editUpdate";
	}

	// 管理员修改用户信息
	@PostMapping("/consumer/userDetail/adminUpdate")
	public String adminUpdate(Userdetail userDetail) {
		restTemplate.postForObject(REST_URL_PREFIX + "/userDetail/adminUpdate", userDetail, int.class);
		return "redirect:selectAll";
	}

	// 用户查看个人信息
	@PassToken
	@GetMapping("/consumer/userDetail/selectById")
	public String selectById(Model m, HttpSession session) {
		int id = (int) session.getAttribute("id");
		Userdetail userDetail = restTemplate.getForObject(REST_URL_PREFIX + "/userDetail/selectById/" + id,
				Userdetail.class);
		m.addAttribute("userDetail", userDetail);
		return "selectUserDetail";
	}

	// 用户修改个人信息
	@PassToken
	@PostMapping("/consumer/userDetail/userUpdate")
	public String userUpdate(Userdetail userDetail) {
		restTemplate.postForObject(REST_URL_PREFIX + "/userDetail/userUpdate", userDetail, int.class);
		return "redirect:selectById";
	}

	@InitBinder
	@PassToken
	public void initBinder(WebDataBinder binder) {
		// binder.setDisallowedFields("name");
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
		binder.registerCustomEditor(Date.class, editor);
	}

	// 上传图片
	@PassToken
	@PostMapping("/consumer/userDetail/upload")
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest req)
			throws FileNotFoundException {

		// 得到从本地上传到服务器的文件上传的路径(绝对路径)
		String path = ResourceUtils.getURL("classpath:").getPath().replace("%20", " ").replace("/", "\\")
				+ "static\\photo";
		String p = req.getServletContext().getRealPath("/");
		System.out.println(path);
		System.out.println(p);
		// 如果此路径不存在，则创建
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 上传文件的名称生成策略，为了不重名 首选截取后缀名 file.getOriginalFilename()获取是完整文件名 整个就是得到以.后面的字符串
		String fileSuffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		// 最后生成的文件名就是以uuid生成随机的+后缀
		String fileName = UUID.randomUUID().toString() + fileSuffix;

		// 得到图片上传的相对路径（因为相对路径放到页面上就可以显示图片）
		String relativePath = "/static/photo/" + fileName;
		File f = new File(dir + "/" + fileName);
		// 上传图片
		try {
			file.transferTo(f);
		} catch (Exception e) {
			throw new RuntimeException("服务器繁忙，上传图片失败");
		}
		// 将相对路径写回（json格式）
		JSONObject jsonObject = new JSONObject();
		// 将图片上传到本地
		jsonObject.put("path", relativePath);
		// 写回
		// res.getWriter().write(jsonObject.toString());
		String json = JSON.toJSONString(jsonObject);
		return json;
	}
}
