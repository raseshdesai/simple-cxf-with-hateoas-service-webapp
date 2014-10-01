package demo.cxf.restful.service;

import demo.cxf.restful.dao.BookShelfDAO;
import demo.cxf.restful.model.Book;
import demo.cxf.restful.model.HATEOASlink;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/*
 * TODO: Book Service class - Get, update, add and delete a specific book
 */

@Path("/bookshelf/books/{id}")
@Produces({"application/xml", "application/json"})
@Consumes({"application/xml", "application/json"})
public class BookService {
    @Context UriInfo uriInfo;
    private BookShelfDAO bookShelfDAO;

    public BookService(BookShelfDAO bookShelfDAO) {
        this.bookShelfDAO = bookShelfDAO;
    }

    public BookShelfDAO getBookShelfDAO() {
        return bookShelfDAO;
    }

    @GET
    public Book getBook(@PathParam("id") String bookId) {
        System.out.println("Get Book with Id: " + bookId);
        Book book = getBookShelfDAO().getBook(bookId);
        book.buildLink(BookService.class,uriInfo);
        if (book == null) {
            ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
            builder.entity("Book Not Found");
            throw new WebApplicationException(builder.build());
        }
        return book;
    }

    @PUT
    public Response updateBook(@PathParam("id") String bookId, Book bookToUpdateWith) {
        System.out.println("Update Book with Id : " + bookId);
        Book book = getBookShelfDAO().getBook(bookId);
        if (book == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        getBookShelfDAO().updateBook(bookToUpdateWith);
        return Response.ok().build();
    }

    //POST - unused, doesn't make sense to add book with a specific id, so this method is present on /books

    @DELETE
    public Response deleteBook(@PathParam("id") String bookId) {
        System.out.println("Delete Book with Id : " + bookId);
        Book book = getBookShelfDAO().getBook(bookId);
        if (book == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        getBookShelfDAO().deleteBook(bookId);
        return Response.ok().build();
    }
}
