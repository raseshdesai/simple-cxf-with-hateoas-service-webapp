package demo.cxf.restful.dao;

import demo.cxf.restful.model.Book;
import demo.cxf.restful.model.Category;

import java.util.*;

/*
 * TODO: Note: Implementation uses static maps as database initialized with some values (not persisted between restart of application)
 */
public class BookShelfDAO {

    private static Map<String, Category> categoryMap = new HashMap<String, Category>();
    private static Map<String, Collection<Book>> bookMap = new HashMap<String, Collection<Book>>();

    static {

        categoryMap.put("001", getCategoryToAddToDatabase("001", "Java"));
        categoryMap.put("002", getCategoryToAddToDatabase("002", "Scala"));

        Collection<Book> booksListForJava = new ArrayList<Book>();
        booksListForJava.add(getBookToAddToDatabase("001", "John Doe", "Rest with CXF", "ISB001", "001"));
        booksListForJava.add(getBookToAddToDatabase("002", "Jack David", "Unrest with SOAP", "ISB002", "001"));

        Collection<Book> booksListForScala = new ArrayList<Book>();
        booksListForScala.add(getBookToAddToDatabase("003", "Carl Johnson", "Scala 101", "ISB003", "002"));

        bookMap.put("001", booksListForJava);
        bookMap.put("002", booksListForScala);
    }

    // /books

    public Collection<Book> getAllBooks(){
        Iterator<String> categoryIdIterator = bookMap.keySet().iterator();
        Collection<Book> allBooks = new ArrayList<Book>();
        while (categoryIdIterator.hasNext()) {
            Collection<Book> booksForCategory = bookMap.get(categoryIdIterator.next());
            allBooks.addAll(booksForCategory);
        }
        return allBooks;
    }

    public void addBookToCategory(String categoryId, Book book) {
        Collection<Book> books = bookMap.get(categoryId);
        books.add(book);
    }

    // /books/{id}

    public Book getBook(String bookId){
        for (String categoryId : bookMap.keySet()) {
            for (Book book : bookMap.get(categoryId)) {
                if (book.getBookId().equalsIgnoreCase(bookId)) {
                    //Note: Dummy implementation to return a new copy of book to avoid getting overridden by service
                    Book clonedBook = new Book();
                    clonedBook.setBookId(book.getBookId());
                    clonedBook.setAuthor(book.getAuthor());
                    clonedBook.setBookName(book.getBookName());
                    clonedBook.setBookISBNnumber(book.getBookISBNnumber());
                    clonedBook.setCategoryId(book.getCategoryId());
                    return clonedBook;
                }
            }
        }
        return null;
    }

    public void updateBook(Book bookToUpdateWith){
        for (String categoryId : bookMap.keySet()) {
            Collection<Book> booksForSpecificCategory = bookMap.get(categoryId);
            for (Book book : booksForSpecificCategory) {
                if (book.getBookId().equalsIgnoreCase(bookToUpdateWith.getBookId())) {
                    booksForSpecificCategory.remove(book);
                    booksForSpecificCategory.add(bookToUpdateWith);
                }
            }
        }
    }

    public void deleteBook(String bookId){
        for (String categoryId : bookMap.keySet()) {
            Collection<Book> booksForSpecificCategory = bookMap.get(categoryId);
            for (Book book : booksForSpecificCategory) {
                if (book.getBookId().equalsIgnoreCase(bookId)) {
                    booksForSpecificCategory.remove(book);
                }
            }
        }
    }

    // /categories

    public Collection<Category> getAllCategories() {
        Collection<Category> clonedCategories = new ArrayList<Category>();
        //Note: Dummy implementation to return a new copy of category to avoid getting overridden by service
        for (String categoryId : categoryMap.keySet()) {
            clonedCategories.add(categoryMap.get(categoryId));
        }
        return clonedCategories;
    }

    public void addCategory(Category category) {
        categoryMap.put(category.getCategoryId(), category);
    }

    // /categories/{id}

    public Category getCategory(String id) {
        Category cat = null;
        //Note: Dummy implementation to return a new copy of category to avoid getting overridden by service
        if (categoryMap.get(id) != null) {
            cat = new Category();
            cat.setCategoryId(categoryMap.get(id).getCategoryId());
            cat.setCategoryName(categoryMap.get(id).getCategoryName());
        }
        return cat;
    }

    public void updateCategory(Category category) {
        categoryMap.put(category.getCategoryId(), category);

    }

    public void deleteCategory(String id) {
        categoryMap.remove(id);
        //Note: Remove association of books
        bookMap.remove(id);
    }

    // /categories/{id}/books

    public Collection<Book> getBooks(String categoryId) {
        return bookMap.get(categoryId);

    }

    public void addBooksToCategory(String categoryId, Collection<Book> books) {
        bookMap.put(categoryId, books);
    }

    private static Book getBookToAddToDatabase(String bookId, String author, String bookName, String isbNumber, String categoryId) {
        Book book = new Book();
        book.setBookId(bookId);
        book.setAuthor(author);
        book.setBookName(bookName);
        book.setBookISBNnumber(isbNumber);
        book.setCategoryId(categoryId);
        return book;
    }

    private static Category getCategoryToAddToDatabase(String categoryId, String categoryName) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);
        return category;
    }
}
