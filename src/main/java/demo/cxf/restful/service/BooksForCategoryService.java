package demo.cxf.restful.service;

import demo.cxf.restful.dao.BookShelfDAO;
import demo.cxf.restful.model.Book;
import demo.cxf.restful.model.Category;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

/*
 * TODO: Books for a specific Category Service class
 */

@Path("/bookshelf/categories/{id}/books")
@Produces({"application/xml", "application/json"})
@Consumes({"application/xml", "application/json"})
public class BooksForCategoryService {

	private BookShelfDAO bookShelfDAO;

    public BooksForCategoryService(BookShelfDAO bookShelfDAO) {
        this.bookShelfDAO = bookShelfDAO;
    }

    public BookShelfDAO getBookShelfDAO() {
		return bookShelfDAO;
	}

    @GET
    public Collection<Book> getAllBooksForCategory(@PathParam("id") String id,@Context UriInfo uriInfo) {
        System.out.println("Get Books called with category id : " + id);
        Category cat = getBookShelfDAO().getCategory(id);
        if (cat == null) {
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).build());
        } else {
            cat.setBooks(getBookShelfDAO().getBooks(id));
            cat.buildURI(BooksForCategoryService.class,uriInfo);
            return cat.getBooks();
        }
    }

    //PUT - unused, since here we're dealing with a list of books for a given category

	@POST
	public Response addBooksToACategory(@PathParam("id") String categoryId, Collection<Book> books) throws URISyntaxException {
		System.out.println("Add Books To A Category with category id : " + categoryId);
		Category cat = getBookShelfDAO().getCategory(categoryId);
		if (cat == null) {
			return Response.status(Status.BAD_REQUEST).build();
		} else {
			getBookShelfDAO().addBooksToCategory(categoryId, books);
			return Response.created(new URI("bookshelf/categories/" + categoryId + "/books")).build();
		}
	}

    //DELETE - unused, but could be implemented if desire is to delete all books for the category
}
