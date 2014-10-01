package demo.cxf.restful.model;

import demo.cxf.restful.service.BooksService;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Book")
public class Book {

	private String bookId;
	private String bookISBNnumber;
	private String bookName;
	
	//Assume for now - one author only
	private String author;

    private String categoryId;
    private HATEOASlink link;

    public HATEOASlink getLink() {
        return link;
    }

    public void setLink(HATEOASlink self) {
        this.link = self;
    }


	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookISBNnumber() {
		return bookISBNnumber;
	}

	public void setBookISBNnumber(String bookISBNnumber) {
		this.bookISBNnumber = bookISBNnumber;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!author.equals(book.author)) return false;
        if (!bookISBNnumber.equals(book.bookISBNnumber)) return false;
        if (!bookId.equals(book.bookId)) return false;
        if (!bookName.equals(book.bookName)) return false;
        if (!categoryId.equals(book.categoryId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookId.hashCode();
        result = 31 * result + bookISBNnumber.hashCode();
        result = 31 * result + bookName.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + categoryId.hashCode();
        return result;
    }

    public Book buildLink(Class classNameOfService,UriInfo uriInfo){
        UriBuilder builder=uriInfo.getBaseUriBuilder();
        builder.path(classNameOfService);
        if(classNameOfService.equals(BooksService.class)){
            this.setLink(new HATEOASlink("self",builder.path("/"+this.bookId).build().toString()));
        }else{
            this.setLink(new HATEOASlink("self",builder.clone().build(this.bookId).toString()));
        }
        return this;
    }
}