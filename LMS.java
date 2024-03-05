import java.util.*;

class Book {
    private String title;
    private String author;

    public Book(String t, String a) {
        this.title = t;
        this.author = a;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void displayInfo() {
        System.out.println("Book Title: " + title);
        System.out.println("Book Author: " + author);
    }
}

class TextBook extends Book {
    private String course;

    public TextBook(String t, String a, String c) {
        super(t, a);
        this.course = c;
    }

    public String getCourse() {
        return course;
    }

    public void displayInfo() {
        super.displayInfo();
        System.out.println("Course: " + course);
    }
}



class Library {

    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Added Successfully!");
    }

    public void displayBooks() {
        for (Book book : books) {
            book.displayInfo();
            System.out.println("-----------------------------");
        }
    }

    public Book getBookByTitle(String title) {

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public void removeBook(Book book) {
        books.remove(book);
        System.out.println("Book taken from the library \n");
    }
}


class User {
    private String name;
    private int ID;

    ArrayList<Book> borrowedBooks;

    public User(String n, int ID) {
        this.name = n;
        this.ID = ID;

        borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
        System.out.println(name + " has borrowed the book:\n");
        book.displayInfo();
    }

    public void returnBook(Book book, Library library) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            System.out.println(name + " has returned the book:\n");
            book.displayInfo();
            library.addBook(book);
        } else {
            System.out.println(name + " did not borrow the book:");
            book.displayInfo();
        }
    }

    public Book getBorrowedBookByTitle(String title) {
        for (Book book : borrowedBooks) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
}



public class LMS {
    public static void main(String[] args) {
        Library allBooks = new Library();
        int selection;
    
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Your Name: ");
        String userName = sc.nextLine();
        
        System.out.println("Enter Your ID: ");
        int userId = sc.nextInt();
        

        User currentUser = new User(userName, userId);
        
        do {
            System.out.println("\n **** Library Management System ****\n");
            System.out.println("1 --> Add Book \n");
            System.out.println("2 --> Display Book \n");
            System.out.println("3 --> Exit \n");
            System.out.println("Enter Your Selection ?");
            selection = sc.nextInt();

            switch (selection) {
                case 1:
                    System.out.println("\nEnter Book Title ");
                    String bookTitle = sc.next();
                    System.out.println("\nEnter Book author ");
                    String bookAuthor = sc.next();

                    System.out.println("\nIs this a Text Book ? (Y/N)");
                    String isTextbook = sc.next();

                    Book newBook;
                    if (isTextbook.equalsIgnoreCase("Y")) {
                        System.out.println("\nEnter Course ");
                        String bookCourse = sc.next();
                        newBook = new TextBook(bookTitle, bookAuthor, bookCourse);
                    } else {
                        newBook = new Book(bookTitle, bookAuthor);
                    }
                    allBooks.addBook(newBook);
                    break;

                case 2:
                    allBooks.displayBooks();
                    System.out.println("1 --> Borrow the Book \n");
                    System.out.println("2 --> Return the Book \n");
                    System.out.println("3 --> Back \n");

                    int borrowBookSelect = sc.nextInt();

                    switch (borrowBookSelect) {
                        case 1:
                            System.out.println("Enter the title of the book you want to borrow: \n");
                            String titleToBorrow = sc.next();
                            Book bookToBorrow = allBooks.getBookByTitle(titleToBorrow);

                            if (bookToBorrow != null) {
                                currentUser.borrowBook(bookToBorrow);
                                allBooks.removeBook(bookToBorrow);
                            } else {
                                System.out.println("Book not found in the library.");
                            }
                            break;

                        case 2:
                            System.out.println("Enter the title of the book you want to return: ");
                            String titleToReturn = sc.next();
                            Book bookToReturn = currentUser.getBorrowedBookByTitle(titleToReturn);

                            if (bookToReturn != null) {
                                currentUser.returnBook(bookToReturn, allBooks);

                            } else {
                                System.out.println("Book not found in the borrowed list.");
                            }
                            break;

                        case 3:
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    break;

                case 3:
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please select again.");

            }
        } while (selection != 3);

        sc.close();
    }
}
