package demo.cxf.restful.service;

import demo.cxf.restful.dao.BookShelfDAO;
import demo.cxf.restful.model.Category;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/*
 * TODO: Categories Service class - Get all categories and add a new category, update and delete unused or don't make sense at this level
 */

@Path("/bookshelf/categories")
@Produces({"application/xml", "application/json"})
@Consumes({"application/xml", "application/json"})
public class CategoriesService {
    @Context
    UriInfo uriInfo;
    private BookShelfDAO bookShelfDAO;

    public CategoriesService(BookShelfDAO bookShelfDAO) {
        this.bookShelfDAO = bookShelfDAO;
    }

    public BookShelfDAO getBookShelfDAO() {
        return bookShelfDAO;
    }

    @GET
    public Collection<Category> getAllCategories() {
        System.out.println("Get all Categories");
        Collection<Category> allCategories = getBookShelfDAO().getAllCategories();

        if (allCategories == null) {
            throw new WebApplicationException(Response.status(Status.NOT_FOUND).build());
        }else{
            for(Category catIterator:allCategories){
                catIterator.buildURI(CategoriesService.class,uriInfo);
            }
            return allCategories;
        }

    }

    //PUT - unused, doesn't make sense to implement update on list of books

    @POST
    public Response addCategory(Category category) {
        System.out.println("Add Category");
        Category cat = getBookShelfDAO().getCategory(category.getCategoryId());
        if (cat != null) {
            return Response.status(Status.BAD_REQUEST).build(); //Category already present, you may add the error message to response as in other examples
        }
        getBookShelfDAO().addCategory(category);
        return Response.ok().build();
    }

    //DELETE - unused, rare but can be implemented if requirement is to delete all books
}
