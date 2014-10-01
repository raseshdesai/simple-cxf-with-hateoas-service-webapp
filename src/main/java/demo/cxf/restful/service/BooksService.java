package demo.cxf.restful.service;

import demo.cxf.restful.dao.BookShelfDAO;
import demo.cxf.restful.model.Book;
import demo.cxf.restful.model.Category;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

/*
 * TODO: Books Service class - Get list of all books or Add a new book, delete and update not used and or don't make sense
 */

@Path("/bookshelf/books")
@Produces({"application/xml", "application/json"})
public class BooksService {
    @Context
    UriInfo uriInfo;
    private BookShelfDAO bookShelfDAO;

    public BooksService(BookShelfDAO bookShelfDAO) {
        this.bookShelfDAO = bookShelfDAO;
    }

    public BookShelfDAO getBookShelfDAO() {
        return bookShelfDAO;
    }

    @GET
    public Response getBooks() {
        System.out.println("Get all Books");
        Collection<Book> allBooks = bookShelfDAO.getAllBooks();

        if (allBooks == null) {
            return Response.status(Status.NOT_FOUND).build();
        }else{
            for(Book bookIterator:allBooks){
                bookIterator.buildLink(BooksService.class,uriInfo);
            }
            return Response.ok(new GenericEntity<Collection<Book>>(allBooks) {}).build();
        }

    }

    //PUT - unused, doesn't make sense to implement update on list of books

    @POST
    @Consumes({"application/xml", "application/json"})
    public Response addBook(Book bookToAdd) throws URISyntaxException {
        System.out.println("Add Book");
        Category category = getBookShelfDAO().getCategory(bookToAdd.getCategoryId());
        if (category == null) {
            ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
            builder.entity("Category not present.");
            return builder.build();
        }

        Book book = getBookShelfDAO().getBook(bookToAdd.getBookId());
        if (book != null) {
            ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
            builder.entity("Book already present.");
            return builder.build();
        }

        getBookShelfDAO().addBookToCategory(category.getCategoryId(), bookToAdd);
        return Response.created(new URI("bookshelf/books/" + bookToAdd.getBookId())).build();
    }

    //DELETE - unused, rare but can be implemented if requirement is to delete all books
}
