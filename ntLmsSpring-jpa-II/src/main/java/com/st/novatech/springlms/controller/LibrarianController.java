package com.st.novatech.springlms.controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.st.novatech.springlms.exception.RetrieveException;
import com.st.novatech.springlms.exception.TransactionException;
import com.st.novatech.springlms.model.Book;
import com.st.novatech.springlms.model.Branch;
import com.st.novatech.springlms.model.BranchCopies;
import com.st.novatech.springlms.service.LibrarianService;

/**
 * Controller for Librarian Services.
 * @author Al-amine AHMED MOUSSA
 */
@RestController
public final class LibrarianController {
	/**
	 * Service class used to handle requests.
	 */
	@Autowired
	private LibrarianService service;

	@RequestMapping({"/branches", "/branches/"})
	public List<Branch> getbranchs() throws TransactionException {
		return service.getAllBranches();
	}

	@RequestMapping({"/books", "/books/"})
	public List<Book> getBooks() throws TransactionException {
		return service.getAllBooks();
	}
	
	@RequestMapping({"/branch/{branchId}", "/branch/{branchId}/"})
	public Branch getBranch(@PathVariable("branchId") final int branchId)
			throws TransactionException {
		final Branch branch = service.getbranch(branchId);
		if (branch == null) {
			throw new RetrieveException("Branch not found");
		} else {
			return branch;
		}
	}
	
	
	@RequestMapping({"/book/{bookId}", "/book/{bookId}/"})
	public Book getBook(@PathVariable("bookId") final int bookId)
			throws TransactionException {
		final Book book = service.getBook(bookId);
		if (book == null) {
			throw new RetrieveException("Book not found");
		} else {
			return book;
		}
	}

	@RequestMapping(path = { "/branch/{branchId}", "/branch/{branchId}/" }, method = RequestMethod.PUT)
	public Branch updateBranch(@PathVariable("branchId") final int branchId,@RequestBody Branch input)
			throws TransactionException {
		final Branch branch = service.getbranch(branchId);
		if (branch == null) {
			throw new RetrieveException("Branch not found");
		} else {
			branch.setName(input.getName());
			branch.setAddress(input.getAddress());
			service.updateBranch(branch);
			return service.getbranch(branchId);
		}
	}

	
	@RequestMapping(path = { "/branch/{branchId}/book/{bookId}",
			"/branch/{branchId}/book/{bookId}/" }, method = RequestMethod.PUT)
	public BranchCopies setBranchCopies(@PathVariable("branchId") int branchId,
			@PathVariable("bookId") int bookId, @RequestParam("noOfCopies") int copies)
	
			throws TransactionException {
	
			
			service.setBranchCopies(service.getbranch(branchId), service.getBook(bookId), copies);

			int foundNumberOfCopies = service.getCopies(service.getBook(bookId), service.getbranch(branchId));
			
			BranchCopies branchCopies = new BranchCopies(service.getBook(bookId),  service.getbranch(branchId), foundNumberOfCopies);
			
			return branchCopies;
			
		
	}
	
	@RequestMapping(path = { "/branch/{branchId}/book/{bookId}",
	                          "/branch/{branchId}/book/{bookId}" }, method = RequestMethod.GET)
	public BranchCopies getBranchCopies(@PathVariable("branchId") int branchId,
			        @PathVariable("bookId") int bookId) throws TransactionException {
		
		BranchCopies branchcopies = new BranchCopies(service.getBook(bookId),service.getbranch(branchId),
				service.getCopies(service.getBook(bookId), service.getbranch(branchId)));
		
		 	return branchcopies;
		
	}
	
	
	@RequestMapping({"/branches/books/copies", "/branches/books/copies/"})
	public Map<Branch, Map<Book, Integer>> getAllCopies() throws TransactionException {
		return service.getAllCopies();
	}
}