package com.st.novatech.springlms.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.st.novatech.springlms.model.Book;
import com.st.novatech.springlms.model.Borrower;
import com.st.novatech.springlms.model.Branch;
import com.st.novatech.springlms.model.Loan;
import com.st.novatech.springlms.model.LoanIdentity;

/**
 * A Data Access Object interface to access the table of outstanding loans.
 *
 * @author Salem Ozaki
 * @author Jonathan Lovelace
 */
@Repository
public interface BookLoansDao extends JpaRepository<Loan, LoanIdentity> {
	/**
	 * Check out a book from a particular branch for a particular borrower, with the
	 * given date out and due date.
	 *
	 * @param book     the book to check out
	 * @param borrower the borrower who is checking out the book
	 * @param branch   the library branch from which the book is being checked out
	 * @param dateOut  the date (and time) the book was checked out
	 * @param dueDate  the date the book is due back
	 * @return the created loan object
	 * @throws SQLException on unexpected error dealing with the database
	 */
	default Loan create(final Book book, final Borrower borrower, final Branch branch, final LocalDateTime dateOut, final LocalDate dueDate) {
		return save(new Loan(book, borrower, branch, dateOut, dueDate));
	}
	/**
	 * Get the loan in which the given borrower checked out the given book from the
	 * given branch.
	 *
	 * @param book     the book in question
	 * @param borrower the borrower in question
	 * @param branch   the branch in question
	 * @return the Loan object giving the dates associated with this loan
	 * @throws SQLException on unexpected error dealing with the database
	 */
	default Loan get(final Book book, final Borrower borrower, final Branch branch) {
		return findById(new LoanIdentity(book, borrower, branch)).orElse(null);
	}
}
