package com;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" }, methods = { RequestMethod.GET, RequestMethod.POST,
		RequestMethod.PUT, RequestMethod.DELETE })

@RequestMapping("companyposts")

public class AppController {

	@Autowired
	CompanyPostsRepository cprRepo;
	@Autowired
	CommentRepository cmntRepo;
	
	@Autowired
	private UsersRepository userRepo;

	@PostMapping("/signup")
	public User signUp(HttpServletRequest req, @RequestBody User user) {

		User result = null;
		try {
			result = userRepo.save(user);

			if (result == null)
				return null;

			HttpSession session = req.getSession(true);
			session.setAttribute("userName", result.getUserName());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/signin")
	public Status signIn(HttpServletRequest req, @RequestBody User user) {
		User temp = userRepo.findByUserName(user.getUserName());

		System.out.println(temp);

		if (temp == null)
			return new Status(false);

		if (temp.getPassword().equals(user.getPassword())) {
			HttpSession session = req.getSession(true);
			session.setAttribute("userName", temp.getUserName());
			return new Status(true);
		}
		return new Status(false);
	}

	@PostMapping("/signout")
	public Status logout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		if (session != null && session.getAttribute("userName") != null) {
			session.invalidate();
			return new Status(true);
		}
		return new Status(false);
	}

	@PostMapping("/post/save")
	public CompanyPosts savePost(@RequestBody CompanyPosts post) {
		return cprRepo.save(post);
	}

	@PostMapping("/comment/save")
	public Comment saveComment(@RequestBody Comment comment) {
		comment.setCpost(new CompanyPosts());
		comment.getCpost().setPid(comment.getFk());
		return cmntRepo.save(comment);
	}

	@DeleteMapping("/delete/companyPosts/{id}")
	public Status deleteCompanyPosts(HttpServletRequest req, @PathVariable Integer pid) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userName") == null)
			return null;
		cprRepo.deleteById(pid);
		return new Status(true);
	}

	@GetMapping("/all")
	public List<CompanyPosts> getCompanyPosts(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userName") == null)
			return null;
		List<CompanyPosts> posts = new ArrayList<CompanyPosts>();
		Iterable<CompanyPosts> iterable = cprRepo.findAll();
		for (CompanyPosts cpost : iterable) {
			posts.add(cpost);
		}
		return posts;

	}

	@PostMapping("/add")
	public CompanyPosts savePost(HttpServletRequest req, @RequestBody CompanyPosts post) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userName") == null)
			return null;
		post = cprRepo.save(post);
		return post;
	}

}
