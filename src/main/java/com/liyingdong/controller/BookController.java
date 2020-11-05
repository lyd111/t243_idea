package com.liyingdong.controller;

import com.liyingdong.model.Book;
import com.liyingdong.serivce.BookService;
import com.liyingdong.util.PageBean;
import com.liyingdong.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 李瀛东
 * @site www.xiaomage.com
 * @company xxx公司
 * @create  2020-10-22 9:46
 *
 */
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/list")
    public String list(Book book,HttpServletRequest req){
        PageBean pageBean=new PageBean();
        pageBean.setRequest(req);
        List<Book> books=null;
        if(book.getBname()!=null){
            book.setBname(StringUtils.toLikeStr(book.getBname()));
            books = this.bookService.MvclistPager(book, pageBean);
        }
         books = this.bookService.MvclistPager(book, pageBean);
        req.setAttribute("bookList",books);
        req.setAttribute("pageBean",pageBean);
        return  "bookList";
    }

    @RequestMapping("/preSave")
    public String preSave(Book book,HttpServletRequest req){
        if(book!=null  && book.getBid()!=null && book.getBid()!=0){
            Book b = this.bookService.selectByPrimaryKey(book.getBid());
            req.setAttribute("book2",b);
        }
        return  "bookEdit";
    }
    @RequestMapping("/add")
    public String add(Book book,HttpServletRequest req){
        this.bookService.insertSelective(book);
        return "redirect:/book/list";
    }
    @RequestMapping("/edit")
    public String edit(Book book,HttpServletRequest req){
        this.bookService.updateByPrimaryKeySelective(book);
        return  "redirect:/book/list";
    }
    @RequestMapping("/del/{bid}")
    public String del(@PathVariable("bid") Integer bid,HttpServletRequest req){
        this.bookService.deleteByPrimaryKey(bid);
        return  "redirect:/book/list";
    }

    @RequestMapping("/del/{typeid}/{bid}")
    public String xxx(@PathVariable("typeid") Integer typeid,@PathVariable("bid") Integer bid,HttpServletRequest req){
        this.bookService.deleteByPrimaryKey(bid);
        return  "redirect:/book/list";
    }

    @RequestMapping("/upload")
    public String upload(HttpServletRequest req, MultipartFile xxx){
        String filename = xxx.getOriginalFilename();
        String contentType = xxx.getContentType();
        try {
            FileUtils.copyInputStreamToFile(xxx.getInputStream(),new File("E:/temp/"+filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  "redirect:/book/list";
    }
}
