package org.example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.models.Author;
import org.example.models.Book;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "BooksServlet", urlPatterns = "/api/books")
public class BooksServlet extends HttpServlet {

    private SessionFactory sessionFactory;

    @Override
    public void init() throws ServletException {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        Book book = getBookById(bookId);

        if (book == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Book not found for id: " + bookId);
            return;
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Book.class, new BookSerializer())
                .create();
        out.print(gson.toJson(book));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("insert".equalsIgnoreCase(action)) {
            insertBook(request, response);
        } else if ("update".equalsIgnoreCase(action)) {
            updateBook(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            deleteBook(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid action specified.");
        }
    }

    private Book getBookById(int id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Book book = session.get(Book.class, id);
            session.getTransaction().commit();
            return book;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void insertBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        int publicationYear = Integer.parseInt(request.getParameter("publication_year"));
        int authorId = Integer.parseInt(request.getParameter("author_id"));
        int price = Integer.parseInt(request.getParameter("price"));

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Book book = new Book();
            book.setTitle(title);
            book.setPublicationYear(publicationYear);
            book.setAuthor(session.get(Author.class, authorId));
            book.setPrice(price);
            session.save(book);
            session.getTransaction().commit();

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Book inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to insert book.");
        }
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        int publicationYear = Integer.parseInt(request.getParameter("publication_year"));
        int authorId = Integer.parseInt(request.getParameter("author_id"));
        int price = Integer.parseInt(request.getParameter("price"));

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Book book = session.get(Book.class, id);
            book.setTitle(title);
            book.setPublicationYear(publicationYear);
            book.setAuthor(session.get(Author.class, authorId));
            book.setPrice(price);
            session.update(book);
            session.getTransaction().commit();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Book updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to update book.");
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Book book = session.get(Book.class, id);
            session.delete(book);
            session.getTransaction().commit();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Book deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to delete book.");
        }
    }

    @Override
    public void destroy() {
        HibernateUtil.shutdown();
    }

}