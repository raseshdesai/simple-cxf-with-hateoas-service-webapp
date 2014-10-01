package demo.cxf.restful.model;

import demo.cxf.restful.service.BookService;
import demo.cxf.restful.service.CategoriesService;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.util.Collection;

@XmlRootElement(name = "Category")
public class Category {

	private String categoryId;

	private String categoryName;

	private Collection<Book> books;
    private HATEOASlink link;

    public HATEOASlink getLink() {
        return link;
    }

    public void setLink(HATEOASlink self) {
        this.link = self;
    }

    public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Collection<Book> getBooks() {
		return books;
	}

	public void setBooks(Collection<Book> books) {
		this.books = books;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!books.equals(category.books)) return false;
        if (!categoryId.equals(category.categoryId)) return false;
        if (!categoryName.equals(category.categoryName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = categoryId.hashCode();
        result = 31 * result + categoryName.hashCode();
        result = 31 * result + books.hashCode();
        return result;
    }

    public Category buildURI(Class classNameOfService,UriInfo uriInfo){
        UriBuilder builder=uriInfo.getBaseUriBuilder();
        builder.path(classNameOfService);
        if(classNameOfService.equals(CategoriesService.class)){
            this.link=new HATEOASlink("self",builder.path("/"+this.getCategoryId()).build().toString());
        }else{
            this.link=new HATEOASlink("self",builder.build(this.getCategoryId()).toString());

        }

        UriBuilder uriBuilder=UriBuilder.fromPath("cxfrest/bookshelf/books/{id}");
        uriBuilder.scheme("http").host("localhost:7001");
        if(this.getBooks()!=null){
        for(Book bookIterator:this.getBooks()){
            UriBuilder uriClone=uriBuilder.clone();
            URI bookUri=uriClone.build(bookIterator.getBookId());
            bookIterator.setLink(new HATEOASlink("self",bookUri.toString()));
        }  }
        return this;
    }
}
