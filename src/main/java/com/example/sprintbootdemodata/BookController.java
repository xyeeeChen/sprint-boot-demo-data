package com.example.sprintbootdemodata;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Api(tags = "Book")
@RestController
@RequestMapping(value = "/api")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @ApiOperation(value = "取得書本", notes = "列出所有書本")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/book", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @ApiOperation(value = "新增書本", notes = "新增書本的內容")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "存檔成功"),
            @ApiResponse(code = 404, message = "不支援")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/v1/book", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto create(
            @ApiParam(required = true, value = "書本內容") @RequestBody BookDto bookDto) {
        Book book = new Book();
        book.setBookid(bookDto.getBookid());
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book = bookRepository.save(book);
        bookDto.setBookid(book.getBookid());
        return bookDto;
    }

    @ApiOperation(value = "取得書本內容", notes = "取得書本內容")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "書本資訊")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/book/{bookid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto get(
            @ApiParam(required = true, name = "bookid", value = "書本ID") @PathVariable Integer bookid) {
        Optional<Book> obook = bookRepository.findById(bookid);
        if (obook.isPresent()) {
            Book book = obook.get();
            BookDto bookDto = new BookDto();
            bookDto.setBookid(book.getBookid());
            bookDto.setName(book.getName());
            bookDto.setAuthor(book.getAuthor());
            return bookDto;
        }
        return null;
    }
}
